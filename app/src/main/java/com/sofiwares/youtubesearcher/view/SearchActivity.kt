package com.sofiwares.youtubesearcher.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.transition.Scene
import android.transition.TransitionManager
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import com.sofiwares.youtubesearcher.util.ErrorID
import com.sofiwares.youtubesearcher.R
import com.sofiwares.youtubesearcher.model.ContentModel
import com.sofiwares.youtubesearcher.viewmodel.ContentListViewModel
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.hello_scene.*
import kotlinx.android.synthetic.main.video_list_scene.*


class SearchActivity : AppCompatActivity() {

    private var mCurrentScene: SearchActivityScene = SearchActivityScene.WELCOME
    private lateinit var mVideoListScene: Scene

    private var mRecyclerView: RecyclerView? = null
    private  var mAdapter: ContentListAdapter? = null
    private lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val model = ViewModelProviders.of(this).get(ContentListViewModel::class.java)
        model.videoList.observe(this, Observer<ArrayList<ContentModel>> { newList ->
            // videoList has changed, update the UI
            if (model.videoList.value!!.size > 0)
                transitionTo(SearchActivityScene.VIDEO_LIST)
            mAdapter?.loadNewItems(newList)
        })

        model.searchError.observe(this, Observer { error -> handleError(error!!) })

        // Create the video list scene, which will be transitioned to after search
        mVideoListScene = Scene.getSceneForLayout(scene_root, R.layout.video_list_scene, this)

        // Set the layout manager for the recyclerView
        mLayoutManager = LinearLayoutManager(this)

        mVideoListScene.setEnterAction({
            mRecyclerView = videoListRecyclerView as RecyclerView

            // use a linear layout manager
            mRecyclerView?.layoutManager = mLayoutManager

            // specify an adapter (see also next example)
            mAdapter = ContentListAdapter(model.videoList.value!!)
            mRecyclerView?.adapter = mAdapter
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as android.widget.SearchView
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(componentName))

        searchView.imeOptions = EditorInfo.IME_ACTION_SEARCH

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                searchView.onActionViewCollapsed()
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean { return true }
        })

        return true
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        // Our activity is searchable, we will reach here once the user
        // has attempted to search
        if (Intent.ACTION_SEARCH == intent?.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)

            //use the query to search our data
            val model = ViewModelProviders.of(this).get(ContentListViewModel::class.java)
            model.searchYoutube(query)

            if (mCurrentScene == SearchActivityScene.WELCOME) {
                // Start animation
                val anim = AnimationUtils.loadAnimation(this, R.anim.rotate)
                logoImageView.startAnimation(anim)
            }
        }
    }

    enum class SearchActivityScene {
        WELCOME,
        VIDEO_LIST
    }

    private fun transitionTo(scene: SearchActivityScene) {
        if (scene != mCurrentScene) {
            when(scene) {
                SearchActivityScene.VIDEO_LIST -> { TransitionManager.go(mVideoListScene) }
                else -> return
            }

            mCurrentScene = scene
        }
    }

    private fun handleError(errorID: ErrorID) {
        if (mCurrentScene == SearchActivityScene.WELCOME) {
            logoImageView.clearAnimation()
        }
        ErrorDialogGenerator.showDialog(this, errorID)
    }

    fun onVideoClick(v: View) {
        val model = ViewModelProviders.of(this).get(ContentListViewModel::class.java)

        val isPlaylist = model.videoList.value!![mRecyclerView?.getChildAdapterPosition(v)!!].isPlaylist
        val id = model.videoList.value!![mRecyclerView?.getChildAdapterPosition(v)!!].contentId

        if (isPlaylist)
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/playlist?list=$id")))
        else
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$id")))
    }
}
