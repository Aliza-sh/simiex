package com.aliza.simiex.utils.di

import androidx.room.Room
import com.aliza.simiex.data.db.AppDatabase
import com.aliza.simiex.data.net.HomeParse
import com.aliza.simiex.data.net.LoginParse
import com.aliza.simiex.data.repository.HomeRepository
import com.aliza.simiex.data.repository.LoginRepository
import com.aliza.simiex.data.session.SessionManager
import com.aliza.simiex.ui.screens.home.HomeViewModel
import com.aliza.simiex.ui.screens.login.LoginViewModel
import com.aliza.simiex.utils.di.CheckConnection.provideCM
import com.aliza.simiex.utils.di.CheckConnection.provideNR
import com.aliza.simiex.utils.net.NetworkChecker
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    //NetworkChecker
    single { NetworkChecker(provideCM(androidContext()), provideNR()) }

    //room db init
    single {
        synchronized(this) {
            Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "app_dataBase.db")
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    //other
    single { SessionManager() }

    //api
    single { LoginParse() }
    single { HomeParse() }

    //Repository
    single<LoginRepository> { LoginRepository(get()) }
    single<HomeRepository> { HomeRepository(get()) }

    //viewModel
    viewModel { LoginViewModel(get()) }
    viewModel { HomeViewModel(get()) }

}
