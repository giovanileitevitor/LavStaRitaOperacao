package br.lavstaritaoperacao.di

import br.lavstaritaoperacao.data.db.LocalDB
import br.lavstaritaoperacao.data.db.LocalRepository
import br.lavstaritaoperacao.data.db.LocalRepositoryImpl
import br.lavstaritaoperacao.domain.usecase.GlobalUseCase
import br.lavstaritaoperacao.domain.usecase.GlobalUseCaseImpl
import br.lavstaritaoperacao.ui.add_service.AddServiceViewModel
import br.lavstaritaoperacao.ui.home_operation.OperationViewModel
import br.lavstaritaoperacao.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

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
    }

    val domainModules = module {
        factory<GlobalUseCase>{
            GlobalUseCaseImpl(
                localRepository = get()
            )
        }

        factory <LocalRepository>{
            LocalRepositoryImpl(
                itemDao = get(),
                serviceDao = get()
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

    }
}