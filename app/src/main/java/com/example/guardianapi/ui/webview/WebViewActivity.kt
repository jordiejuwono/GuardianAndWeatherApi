package com.example.guardianapi.ui.webview

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.guardianapi.R
import com.example.guardianapi.base.arch.BaseActivity
import com.example.guardianapi.data.local.room.entity.Articles
import com.example.guardianapi.databinding.ActivityWebViewBinding
import com.example.guardianapi.ui.article.articlelist.ArticleListFragment
import com.example.guardianapi.utils.ArticleData
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewActivity :
    BaseActivity<ActivityWebViewBinding, WebViewViewModel>(ActivityWebViewBinding::inflate),
    WebViewContract.View {

    override fun initView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.text_web_title)

        getViewBinding().wvArticle.apply {
            webViewClient = WebViewClient()
            val url = intent.getStringExtra(ArticleData.WEB_VIEW_URL)
            if (url != null) {
                loadUrl(url)
            }
        }
    }

    private fun saveArticle() {
        val webUrl = intent.getStringExtra(ArticleData.WEB_VIEW_URL)
        val articleTitle = intent.getStringExtra(ArticleData.ARTICLE_DATA_TITLE)
        val articleDate = intent.getStringExtra(ArticleData.ARTICLE_DATA_DATE)
        val sectionId = intent.getStringExtra(ArticleData.ARTICLE_DATA_SECTION_ID)
        val sectionName = intent.getStringExtra(ArticleData.ARTICLE_DATA_SECTION_NAME)
        val articleImage = intent.getStringExtra(ArticleData.ARTICLE_DATA_IMAGE)

        if (articleTitle != null) {
            getViewModel().saveArticle(
                Articles(
                    0,
                    articleTitle,
                    webUrl,
                    articleDate,
                    sectionId,
                    sectionName,
                    articleImage
                )
            )
        } else {
            Toast.makeText(this, getString(R.string.text_already_saved), Toast.LENGTH_SHORT).show()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val articleTitle = intent.getStringExtra(ArticleData.ARTICLE_DATA_TITLE)
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return when (item.itemId) {
            R.id.action_share -> {
                shareUrl()
                true
            }
            R.id.action_save -> {
                MaterialAlertDialogBuilder(this)
                    .apply {
                        setTitle(getString(R.string.text_save_article))
                            .setMessage(getString(R.string.text_save_confirmation))
                            .setPositiveButton(getString(R.string.text_yes)) { dialog, _ ->

                                saveArticle()

                                val snackBar = Snackbar.make(
                                    getViewBinding().root,
                                    getString(R.string.text_save_success),
                                    Snackbar.LENGTH_SHORT
                                )
                                snackBar.setAction(getString(R.string.text_dismiss)) {
                                    snackBar.dismiss()
                                }
                                snackBar.setActionTextColor(
                                    ContextCompat.getColor(
                                        this@WebViewActivity,
                                        R.color.white
                                    )
                                )
                                if (articleTitle != null) {
                                    snackBar.show()
                                }
                                dialog.dismiss()
                            }
                            .setNegativeButton(getString(R.string.text_cancel)) { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create().show()
                    }
                true
            }
            else -> true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun shareUrl() {
        val articleUrl: String? = intent.getStringExtra(ArticleData.WEB_VIEW_URL)

        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, articleUrl)
        intent.type = "text/plain"

        startActivity(Intent.createChooser(intent, getString(R.string.text_share)))
    }


}