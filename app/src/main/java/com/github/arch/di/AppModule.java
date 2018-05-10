package com.github.arch.di;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import com.github.arch.data.locale.LocalRepositoryImpl;
import com.github.arch.data.locale.RepoInformationDAO;
import com.github.arch.data.locale.ReposDao;
import com.github.arch.data.locale.ReposDatabase;
import com.github.arch.data.remote.GithubService;
import com.github.arch.data.remote.RemoteRepositoryImpl;
import com.github.arch.domain.LoadReposUseCase;
import com.github.arch.domain.LocalRepository;
import com.github.arch.domain.RemoteRepository;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;

import javax.inject.Singleton;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import dagger.Module;
import dagger.Provides;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    @NonNull
    private Context mContext;

    public AppModule(@NonNull Context appContext) {
        mContext = appContext;
    }

    @Provides
    @Singleton
    GithubService provideGithubService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient client=new OkHttpClient();
        try {
            client = new OkHttpClient.Builder()
                    .sslSocketFactory(new TLSSocketFactory())
                    .addInterceptor(interceptor)
                    .build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        return new Retrofit.Builder()
                .baseUrl(GithubService.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                //.addConverterFactory()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(GithubService.class);
    }

    @Provides
    @Singleton
    ReposDatabase provideReposDatabase() {
        return Room.databaseBuilder(
                mContext,
                ReposDatabase.class,
                ReposDatabase.DATABASE_NAME).
                build();
    }

    @Provides
    @Singleton
    ReposDao provideReposDao(@NonNull ReposDatabase reposDatabase) {
        return reposDatabase.reposDao();
    }

    @Provides
    @Singleton
    RepoInformationDAO provideRepoInformationDAO(@NonNull ReposDatabase reposDatabase) {
        return reposDatabase.repoInformationDAO();
    }

    @Provides
    @Singleton
    LoadReposUseCase provideLoadReposUseCase(@NonNull LocalRepository localRepository,
                                             @NonNull RemoteRepository remoteRepository) {
        return new LoadReposUseCase(remoteRepository, localRepository);
    }

    @Provides
    @Singleton
    LocalRepository provideLocalRepository(@NonNull ReposDao reposDao, @NonNull RepoInformationDAO repoInformationDAO) {
        return new LocalRepositoryImpl(reposDao, repoInformationDAO);
    }

    @Provides
    @Singleton
    RemoteRepository provideRemoteRepository(@NonNull GithubService githubService) {
        return new RemoteRepositoryImpl(githubService);
    }

    public static class TLSSocketFactory extends SSLSocketFactory {

        private SSLSocketFactory delegate;

        public TLSSocketFactory() throws KeyManagementException, NoSuchAlgorithmException {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, null, null);
            delegate = context.getSocketFactory();
        }

        @Override
        public String[] getDefaultCipherSuites() {
            return delegate.getDefaultCipherSuites();
        }

        @Override
        public String[] getSupportedCipherSuites() {
            return delegate.getSupportedCipherSuites();
        }

        @Override
        public Socket createSocket() throws IOException {
            return enableTLSOnSocket(delegate.createSocket());
        }

        @Override
        public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
            return enableTLSOnSocket(delegate.createSocket(s, host, port, autoClose));
        }

        @Override
        public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
            return enableTLSOnSocket(delegate.createSocket(host, port));
        }

        @Override
        public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
            return enableTLSOnSocket(delegate.createSocket(host, port, localHost, localPort));
        }

        @Override
        public Socket createSocket(InetAddress host, int port) throws IOException {
            return enableTLSOnSocket(delegate.createSocket(host, port));
        }

        @Override
        public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
            return enableTLSOnSocket(delegate.createSocket(address, port, localAddress, localPort));
        }

        private Socket enableTLSOnSocket(Socket socket) {
            if(socket != null && (socket instanceof SSLSocket)) {
                ((SSLSocket)socket).setEnabledProtocols(new String[] {"TLSv1.1", "TLSv1.2"});
            }
            return socket;
        }

    }
}