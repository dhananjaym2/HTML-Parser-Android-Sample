package html.parser.android.sample

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException

/**
 * source: https://medium.com/@ssaurel/learn-to-parse-html-pages-on-android-with-jsoup-2a9b0da0096f
 */
class MainActivity : AppCompatActivity() {

    lateinit var doc: Document

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Loading data", Snackbar.LENGTH_LONG)
                    .setAction(android.R.string.ok, null).show()

            Thread(Runnable {
                val builder = StringBuilder()

                try {
                    val doc = Jsoup.connect("http://www.ssaurel.com/blog").get()
                    val title = doc.title()
                    val links = doc.select("a[href]")

                    builder.append(title).append("\n")

                    for (link in links) {
                        builder.append("\n").append("Link : ").append(link.attr("href"))
                                .append("\n").append("Text : ").append(link.text())
                    }
                } catch (e: IOException) {
                    builder.append("Error : ").append(e.localizedMessage).append("\n")
                }

                runOnUiThread { resultTextView.setText(builder.toString()) }
            }).start()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
