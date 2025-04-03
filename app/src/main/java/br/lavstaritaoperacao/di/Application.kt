package br.lavstaritaoperacao.di

import android.app.Application
import br.lavstaritaoperacao.di.AppComponent.getAllModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level

class Application(): Application() {

    override fun onCreate(){
        super.onCreate()
        initDI()
    }

    private fun initDI(){
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@Application)
            androidFileProperties()
            koin.loadModules(getAllModules())
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }

}