package br.lavstaritaoperacao.di

import br.lavstaritaoperacao.di.AppModules.dataModules
import br.lavstaritaoperacao.di.AppModules.domainModules
import br.lavstaritaoperacao.di.AppModules.presentationModules
import org.koin.core.module.Module

object AppComponent {

    fun getAllModules(): List<Module> = listOf(*getAppModules())

    private fun getAppModules(): Array<Module>{
        return arrayOf(
            presentationModules,
            domainModules,
            dataModules
        )
    }
}