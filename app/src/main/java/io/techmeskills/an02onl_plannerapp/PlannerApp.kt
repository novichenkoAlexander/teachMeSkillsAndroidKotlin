package io.techmeskills.an02onl_plannerapp

import android.app.Application
import io.techmeskills.an02onl_plannerapp.screen.main.MainViewModel
import io.techmeskills.an02onl_plannerapp.screen.main.NoteViewModel
import io.techmeskills.an02onl_plannerapp.screen.main.database.DataBaseConstructor
import io.techmeskills.an02onl_plannerapp.screen.main.database.NoteDatabase
import io.techmeskills.an02onl_plannerapp.screen.splash.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class PlannerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PlannerApp)
            modules(listOf(viewModels, dataBaseModule))
        }
    }

    private val viewModels = module {
        viewModel { SplashViewModel() }
        viewModel { MainViewModel(get()) }
        viewModel { NoteViewModel(get()) }
    }

    private val dataBaseModule = module {
        single { DataBaseConstructor.create(get()) }
        factory { get<NoteDatabase>().notesDao() }
    }


}