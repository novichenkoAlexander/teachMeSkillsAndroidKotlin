package io.techmeskills.an02onl_plannerapp

import android.app.Application
import io.techmeskills.an02onl_plannerapp.screen.login.LoginScreenViewModel
import io.techmeskills.an02onl_plannerapp.screen.main.MainViewModel
import io.techmeskills.an02onl_plannerapp.screen.main.NoteViewModel
import io.techmeskills.an02onl_plannerapp.cloud.ApiInterface
import io.techmeskills.an02onl_plannerapp.database.DataBaseConstructor
import io.techmeskills.an02onl_plannerapp.database.MyAppDataBase
import io.techmeskills.an02onl_plannerapp.datastore.AppSettings
import io.techmeskills.an02onl_plannerapp.repositories.CloudRepository
import io.techmeskills.an02onl_plannerapp.repositories.NotesRepository
import io.techmeskills.an02onl_plannerapp.repositories.UsersRepository
import io.techmeskills.an02onl_plannerapp.screen.settings.SettingsViewModel
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
            modules(listOf(viewModels, dataBaseModule, repositories, cloud))
        }
    }

    private val viewModels = module {
        viewModel { SplashViewModel() }
        viewModel { MainViewModel(get(), get(), get()) }
        viewModel { NoteViewModel(get()) }
        viewModel { LoginScreenViewModel(get()) }
        viewModel { SettingsViewModel(get()) }
    }

    private val dataBaseModule = module {
        single { DataBaseConstructor.create(get()) }
        factory { get<MyAppDataBase>().notesDao() }
        factory { get<MyAppDataBase>().usersDao() }
        single { AppSettings(get()) }
    }

    private val repositories = module {
        factory { UsersRepository(get(), get(), get()) }
        factory { NotesRepository(get(), get(), get()) }
        factory { CloudRepository(get(), get(), get()) }
    }

    private val cloud = module {
        factory { ApiInterface.get() }
    }

}