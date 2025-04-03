package br.lavstaritaoperacao.di

import br.lavstaritaoperacao.data.db.LocalDB
import br.lavstaritaoperacao.data.db.LocalRepository
import br.lavstaritaoperacao.data.db.LocalRepositoryImpl
import br.lavstaritaoperacao.data.supabase.ServiceRepository
import br.lavstaritaoperacao.data.supabase.ServiceRepositoryImpl
import br.lavstaritaoperacao.domain.usecase.GlobalUseCase
import br.lavstaritaoperacao.domain.usecase.GlobalUseCaseImpl
import br.lavstaritaoperacao.domain.usecase.ServiceUseCase
import br.lavstaritaoperacao.ui.client.home_client.ClientHomeViewModel
import br.lavstaritaoperacao.ui.login.LoginViewModel
import br.lavstaritaoperacao.ui.operation.add_service.AddServiceViewModel
import br.lavstaritaoperacao.ui.operation.configuration.ConfigurationViewModel
import br.lavstaritaoperacao.ui.operation.edit_service.EditServiceViewModel
import br.lavstaritaoperacao.ui.operation.home_operation.OperationViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


object AppModules {

    val presentationModules = module {
        viewModel { ClientHomeViewModel(
            serviceUseCase = get(),
        ) }

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
                localRepository = get()
            )
        }

        single { ServiceUseCase(get()) }

    }


    val dataModules = module {

        single {
            HttpClient(Android) {
                install(ContentNegotiation) {
                    json(Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true

                    })
                }
            }
        }

        single<ServiceRepository> { ServiceRepositoryImpl(get()) }

        factory <LocalRepository>{
            LocalRepositoryImpl(
                itemDao = get(),
                serviceDao = get()
            )
        }

        single { LocalDB.createDatabase(context = get()) }

        single { get<LocalDB>().itemDao() }

        single { get<LocalDB>().serviceDao() }

    }
}