package com.example.guardianapi.ui.article.savedarticles

import android.app.AlertDialog
import android.content.Intent
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.example.guardianapi.R
import com.example.guardianapi.base.arch.BaseFragment
import com.example.guardianapi.data.local.room.entity.Articles
import com.example.guardianapi.databinding.FragmentSavedArticlesBinding
import com.example.guardianapi.ui.adapter.SavedArticlesAdapter
import com.example.guardianapi.ui.webview.WebViewActivity
import com.example.guardianapi.utils.ArticleData
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedArticlesFragment :
    BaseFragment<FragmentSavedArticlesBinding, SavedArticlesViewModel>(FragmentSavedArticlesBinding::inflate),
    SavedArticlesContract.View {

    private lateinit var adapter: SavedArticlesAdapter
    private var articleList: List<Articles>? = null

    override fun initView() {
        initList()
        setRefreshLayout()
        setInfoMenuClickListener()
    }

    private fun setInfoMenuClickListener() {
        val menuInfo = activity?.findViewById<ImageView>(R.id.iv_info)
        menuInfo?.setOnClickListener {
            val dialogView: View =
                LayoutInflater.from(requireContext()).inflate(R.layout.saved_articles_info, null)
            val dialogBuilder = AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setPositiveButton("Dismiss") { dialog, _ ->
                    dialog.dismiss()
                }
            val lottie = dialogView.findViewById<LottieAnimationView>(R.id.lottie_info)
            lottie.repeatCount = LottieDrawable.INFINITE
            dialogBuilder.show()
        }
    }

    private fun showLottie() {
        if (adapter.itemCount == 0) {
            getViewBinding().lottieSaved.visibility = View.VISIBLE
            getViewBinding().tvInfo.visibility = View.VISIBLE
        } else {
            getViewBinding().lottieSaved.visibility = View.GONE
            getViewBinding().tvInfo.visibility = View.GONE
        }
    }

    private fun setRefreshLayout() {
        getViewBinding().srlContent.setOnRefreshListener {
            getViewBinding().srlContent.isRefreshing = false
            getData()
        }
    }

    override fun onResume() {
        super.onResume()
        getViewBinding().pbArticleClicked.visibility = View.GONE
    }

    override fun initList() {
        adapter = SavedArticlesAdapter(object : SavedArticlesAdapter.SavedArticlesClickListener {
            override fun onItemClick(items: Articles) {
                getViewBinding().pbArticleClicked.visibility = View.VISIBLE
                val intent = Intent(requireContext(), WebViewActivity::class.java)
                intent.putExtra(ArticleData.WEB_VIEW_URL, items.webUrl)
                startActivity(intent)
            }

        })
        getViewBinding().rvSavedArticles.adapter = adapter
        getViewBinding().rvSavedArticles.layoutManager = LinearLayoutManager(requireContext())

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val savedArticle = articleList?.get(position)
                savedArticle?.let { getViewModel().deleteSavedArticle(it) }
//                getData()
//                observeData()
//                initList()
                val snackBar =
                    Snackbar.make(requireView(), getString(R.string.text_removed_saved), Snackbar.LENGTH_SHORT)
                snackBar.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                snackBar.setAction(getString(R.string.text_undo)) {
                    getViewModel().undoArticleDelete(savedArticle!!)
                    Toast.makeText(requireContext(), getString(R.string.text_undo_success), Toast.LENGTH_SHORT).show()
//                    getData()
//                    observeData()
//                    initList()
                    snackBar.dismiss()
                }
                snackBar.show()
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(getViewBinding().rvSavedArticles)
        }

    }

    override fun getData() {
//        MainScope().launch {
//            getViewModel().showSavedArticlesList()
//        }
    }

    override fun observeData() {
        getViewModel().showSavedArticlesList().observe(this) {
            showLoading(false)
            setDataAdapter(it)
            showLottie()
        }
//        getViewModel().showSavedArticlesListLiveData().observe(this) {
//            when (it) {
//                is Resource.Loading -> {
//                    showLoading(true)
//                    showContent(false)
//                    showError(false, null)
//                }
//                is Resource.Success -> {
//                    showLoading(false)
//                    showContent(true)
//                    showError(false, null)
//                    initList()
//                    setDataAdapter(it.data)
//                    showLottie()
//
//                }
//                is Resource.Error -> {
//                    showLoading(false)
//                    showContent(false)
//                    showError(true, it.message)
//                }
//            }
//        }
    }

    override fun setDataAdapter(data: List<Articles>?) {
        data?.let { result ->
            adapter.setData(result)
            articleList = result
        }
    }

    override fun showLoading(isVisible: Boolean) {
        getViewBinding().pbLoading.isVisible = isVisible
    }

    override fun showContent(isVisible: Boolean) {
        getViewBinding().srlContent.isVisible = isVisible
    }

    override fun showError(isErrorEnabled: Boolean, msg: String?) {
        msg?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

}