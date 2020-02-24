package com.example.newsapikotlin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapikotlin.R
import com.example.newsapikotlin.model.Article
import com.example.newsapikotlin.toSimpleString
import kotlinx.android.synthetic.main.articles_item.view.*

class ArticlesListAdapter(var articlesList: List<Article> = ArrayList()) :
    RecyclerView.Adapter<ArticlesListAdapter.ArticlesViewHolder>() {

    var mClickListener: ClickListener? = null
    fun setOnClickListener(clickListener: ClickListener) {
        this.mClickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.articles_item, parent, false)
        return ArticlesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articlesList.size
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        holder.bindArticle(articlesList.get(position))
    }

    fun updateList(article: List<Article>) {
        this.articlesList = article
        notifyDataSetChanged()
    }

    inner class ArticlesViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview),
        View.OnClickListener {

        private var view: View = itemView
        private lateinit var articlesResult: Article

        init {
            itemview.setOnClickListener(this)
        }

        fun bindArticle(article: Article) {
            this.articlesResult = article

//            Picasso.get().load(articlesResult.urlToImage)
//                .placeholder(R.drawable.glaciar_grey_stoke) ///tempo
//                .into(view.image)
            Glide.with(view.context).load(articlesResult.urlToImage).into(view.image)

            view.txt_article_title.text = articlesResult.title
            view.txt_article_date.text =
                toSimpleString(articlesResult.publishedAt)//call directly bcoz toSimpleString is fun!!!
            view.txt_article_description.text = articlesResult.description
        }

        override fun onClick(v: View?) {
            mClickListener?.onClick(articlesResult)
        }

    }

    interface ClickListener {
        fun onClick(article: Article)
    }
}