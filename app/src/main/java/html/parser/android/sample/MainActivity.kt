package html.parser.android.sample

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jsoup.Jsoup
import java.io.IOException

/**
 * source: https://medium.com/@ssaurel/learn-to-parse-html-pages-on-android-with-jsoup-2a9b0da0096f
 */
class MainActivity : AppCompatActivity() {

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

                runOnUiThread { resultTextView.text = builder.toString() }
            }).start()

        }
    }
}