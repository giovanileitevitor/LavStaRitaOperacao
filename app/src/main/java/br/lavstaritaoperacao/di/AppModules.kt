package br.lavstaritaoperacao.di

import br.lavstaritaoperacao.data.db.LocalDB
import br.lavstaritaoperacao.data.db.LocalRepository
import br.lavstaritaoperacao.data.db.LocalRepositoryImpl
import br.lavstaritaoperacao.data.remote.RemoteRepository
import br.lavstaritaoperacao.data.remote.RemoteRepositoryImpl
import br.lavstaritaoperacao.data.remote.api.SupabaseApi
import br.lavstaritaoperacao.domain.usecase.GlobalUseCase
import br.lavstaritaoperacao.domain.usecase.GlobalUseCaseImpl
import br.lavstaritaoperacao.ui.operation.add_service.AddServiceViewModel
import br.lavstaritaoperacao.ui.operation.home_operation.OperationViewModel
import br.lavstaritaoperacao.ui.login.LoginViewModel
import br.lavstaritaoperacao.ui.operation.configuration.ConfigurationViewModel
import br.lavstaritaoperacao.ui.operation.edit_service.EditServiceViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


object AppModules {

    private const val apiService = "apiService"
    //const val baseUrlLocal = "http://192.168.0.183:8080/"
    //const val baseUrlLocal = "http://192.168.0.149:8080/"
    //private const val baseUrlLocal = "http://10.140.68.110:8080/"
    //private const val baseUrlold = "http://35.231.76.252:8080/"
    const val baseUrlLocal = "http://34.139.189.29:8080/"
    private const val startAppResponseToStartApp = "StartAppResponseToStartApp"
    private const val parametersResponseToParameters = "ParametersResponseToParameters"
    private const val dashBoardsResponseToDashBoards = "DashBoardsResponseToDashBoards"

    val presentationModules = module {
        viewModel { LoginViewModel(
                globalUseCase = get()
            ) }

        viewModel { OperationViewModel(
                globalUseCase = get()
            ) }

        viewModel { AddServiceViewModel(
                globalUseCase = get()
            ) }

        viewModel { EditServiceViewModel(
                globalUseCase = get()
            ) }

        viewModel { ConfigurationViewModel(
            context = androidContext(),
            globalUseCase = get()
            ) }
    }

    val domainModules = module {
        factory<GlobalUseCase>{
            GlobalUseCaseImpl(
                localRepository = get(),
                remoteRepository = get()
            )
        }

        factory <LocalRepository>{
            LocalRepositoryImpl(
                itemDao = get(),
                serviceDao = get()
            )
        }

        factory <RemoteRepository>{
            RemoteRepositoryImpl(
                supabaseApi = get()
            )
        }
    }


    val dataModules = module {
//        single<SharedPrefDataSource>{
//            SharedDataSourceImpl(
//                context = androidContext()
//            )
//        }

        single { LocalDB.createDatabase(context = get()) }

        single { get<LocalDB>().itemDao() }

        single { get<LocalDB>().serviceDao() }

//        single {
//            Retrofit.Builder()
//                .baseUrl("https://jmsdxcvdtcvjebjbbkwt.supabase.co") // Substitua pela URL da sua API Supabase
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(SupabaseApi::class.java)
//        }

        single {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        }

        single {
            Retrofit.Builder()
                .baseUrl("https://jmsdxcvdtcvjebjbbkwt.supabase.co") // Substitua pela URL do seu Supabase
                .client(get())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        }

        single { get<Retrofit>().create(SupabaseApi::class.java) }

    }
}