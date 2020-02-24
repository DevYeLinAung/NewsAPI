package com.example.newsapikotlin.ui


import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapikotlin.R
import com.example.newsapikotlin.model.Article
import com.example.newsapikotlin.model.ArticlesResult
import com.example.newsapikotlin.ui.adapter.ArticlesListAdapter
import com.example.newsapikotlin.viewmodel.ArticlesViewModel
import com.example.newsapikotlin.viewmodel.SelectedArticleViewModel
import kotlinx.android.synthetic.main.fragment_articles_list.*

class ArticlesListFragment : Fragment(), ArticlesListAdapter.ClickListener {


    private lateinit var articlesListAdapter: ArticlesListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var articlesViewModel: ArticlesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_articles_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewManager = LinearLayoutManager(activity) // can use context instead of activity
        articlesListAdapter = ArticlesListAdapter()
        recycler_articles.adapter = articlesListAdapter
        recycler_articles.layoutManager = viewManager
        articlesListAdapter.setOnClickListener(this)
        observeViewModel()
    }

    fun observeViewModel() {
        articlesViewModel = ViewModelProviders
            .of(this)/*lifecycler owner*/
            .get(ArticlesViewModel::class.java)

        articlesViewModel.getResults().observe(
            this, Observer<ArticlesResult> { result ->
                recycler_articles.visibility = View.VISIBLE
                articlesListAdapter.updateList(result.articles)//ArticlesResult.articles
            }
        )

        articlesViewModel.getError().observe(
            this, Observer<Boolean> { isError ->
                if (isError) {
                    txt_error.visibility = View.VISIBLE
                    recycler_articles.visibility = View.GONE
                } else {
                    txt_error.visibility = View.GONE
                }
            }
        )

        articlesViewModel.getLoading().observe(
            this, Observer<Boolean> { isLoading ->
                loadingView.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE

                if (isLoading) {
                    txt_error.visibility = View.GONE
                    recycler_articles.visibility = View.GONE
                }
            }
        )
    }

    override fun onResume() {
        super.onResume()
        articlesViewModel.loadResults()
    }

    override fun onClick(article: Article) {
        if (!TextUtils.isEmpty(article.url)) {
            val selectedArticleViewModel: SelectedArticleViewModel =
                ViewModelProviders.of(activity!!).get(SelectedArticleViewModel::class.java)
            selectedArticleViewModel.selectedArticle(article)
                activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.screen_container, DetailsFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}
