package com.example.guardianapi.di

import com.example.guardianapi.base.arch.ViewModelFactory
import com.example.guardianapi.ui.article.articlebysection.ArticleListSectionRepository
import com.example.guardianapi.ui.article.articlebysection.ArticleListSectionViewModel
import com.example.guardianapi.ui.article.articlelist.ArticleListRepository
import com.example.guardianapi.ui.article.articlelist.ArticleListViewModel
import com.example.guardianapi.ui.article.searcharticle.SearchArticleRepository
import com.example.guardianapi.ui.article.searcharticle.SearchArticleViewModel
import com.example.guardianapi.ui.webview.WebViewRepository
import com.example.guardianapi.ui.webview.WebViewViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object ViewModelModule {
    @Provides
    @ActivityScoped
    fun provideArticleListViewModel(
        articleListRepository: ArticleListRepository
    ) : ArticleListViewModel {
        return ViewModelFactory(ArticleListViewModel(articleListRepository)).create(
            ArticleListViewModel::class.java
        )
    }

    @Provides
    @ActivityScoped
    fun provideSearchedArticleListViewModel(
        articleListRepository: SearchArticleRepository
    ) : SearchArticleViewModel {
        return ViewModelFactory(SearchArticleViewModel(articleListRepository)).create(
            SearchArticleViewModel::class.java
        )
    }

    @Provides
    @ActivityScoped
    fun provideArticleListListSectionViewModel(
        articleListSectionRepository: ArticleListSectionRepository
    ) : ArticleListSectionViewModel {
        return ViewModelFactory(ArticleListSectionViewModel(articleListSectionRepository)).create(
            ArticleListSectionViewModel::class.java
        )
    }

    @Provides
    @ActivityScoped
    fun provideWebViewViewModel(
        webViewRepository: WebViewRepository
    ) : WebViewViewModel {
        return ViewModelFactory(WebViewViewModel(webViewRepository)).create(
            WebViewViewModel::class.java
        )
    }
}