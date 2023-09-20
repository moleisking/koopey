package com.koopey.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Scott on 24/06/2018.
 */

public class Articles implements Serializable, Comparator<Articles>, Comparable<Articles> {

    private final String LOG_HEADER = "ARTS";
    public static final String MY_ARTICLES_FILE_NAME = "my_articles.dat";
    public static final String ARTICLE_SEARCH_RESULTS_FILE_NAME = "article_search_results.dat";
    public static final String ARTICLE_WATCH_LIST_FILE_NAME = "article_watch_list.dat";
    protected List<Article> articles;
    public String fileType;

    public Articles()
    {
        this.articles = new ArrayList<Article>(0);
    }

    public Articles(String fileType)    {
        this.articles = new  ArrayList<Article>(0);
        this.fileType = fileType;
    }

    public Articles( Article[] article)    {
        this.articles = new  ArrayList<Article>(2);
        for (int i =0; i < article.length;i++) {
            this.articles.add(article[i]);
        }
    }

    public Articles ( Articles articles)    {
        this.articles = new  ArrayList<Article>();
        for (int i =0; i < articles.size();i++)        {
            this.articles.add(articles.get(i));
        }
    }

    @Override
    public int compare(Articles o1, Articles o2) {
        //-1 not the same, 0 is same, 1 > is same but larger
        int result = -1;
        if (o1.size() < o2.size()) {
            result = -1;
        } else if (o1.size() > o2.size()) {
            result = 1;
        } else {
            o1.sort();
            o2.sort();
            for (int i = 0; i < o1.size(); i++) {
                if (!o1.contains(o2.get(i))) {
                    result = -1;
                    break;
                } else if (i == o2.size() - 1) {
                    result = 0;
                    break;
                }
            }
        }
        return result;
    }

    public int compareTo(Articles o) {
        return this.compare(this, o);
    }

    /*********  Create *********/

    public void add(Article p)
    {
        try
        {
            articles.add(p);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }

    }

    /*********  Read *********/

    public Article get(int index)
    {
        return articles.get(index);
    }

    public Article get(Article article)    {
        Article result = null;
        for (int i =0; i < this.articles.size();i++)        {
            if ( this.articles.get(i).id.equals(article.id))            {
                result = this.articles.get(i);
                break;
            }
        }
        return result;
    }

    public Article get(String id)    {
        Article result = null;
        for (int i =0; i < articles.size();i++)        {
            if (articles.get(i).id.equals(id))            {
                result = articles.get(i);
                break;
            }
        }
        return result;
    }

    public List<Article> get() {
        return articles;
    }

    /*********  Checks *********/

    public boolean contains(Article article)    {
        boolean result = false;
        for (int i =0; i < this.articles.size();i++)        {
            if (this.articles.get(i).id.equals(article.id))            {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean contains(Articles articles)
    {
        boolean result = false;
        int counter = 0;
        for (int i =0; i < this.articles.size();i++)        {
            if(this.contains(this.articles.get(i)))            {
                counter++;
                if (counter == articles.size())                {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    public boolean isEmpty()
    {
        return this.size() == 0 ? true : false;
    }

    /*********  Delete *********/

    public void remove(Article p)
    {
        articles.remove(p);
    }

    public int size()
    {
        return articles.size();
    }

    public List<Article> getProductList()
    {
        return articles;
    }

    public void sort() {
        Collections.sort(articles);
    }

    protected void setArticleList(List<Article> articles)
    {
        this.articles = articles;
    }

}
