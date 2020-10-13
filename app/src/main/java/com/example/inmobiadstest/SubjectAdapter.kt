package com.example.inmobiadstest

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.inmobi.ads.InMobiNative
import com.squareup.picasso.Picasso


class SubjectAdapter(var subjectArray: ArrayList<Subject>, var context: Context, val fm: FragmentManager) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_CONTENT_FEED = 0

    private val VIEW_TYPE_INMOBI_STRAND = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_CONTENT_FEED) {
            var view =
                    LayoutInflater.from(context).inflate(com.example.inmobiadstest.R.layout.subject_items, parent, false)
            Viewholder(view)
        } else {
            var view = LayoutInflater.from(context).inflate(com.example.inmobiadstest.R.layout.ad_item_list, parent, false)
            AdsViewHolder(view)
        }
    }

    override fun getItemCount(): Int = subjectArray.size

    class Viewholder(view: View) : RecyclerView.ViewHolder(view) {
        var subName: TextView = view.findViewById(com.example.inmobiadstest.R.id.subName)
        var subImage: ImageView = view.findViewById(com.example.inmobiadstest.R.id.subImage)

    }

    class AdsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var icon: ImageView? = view.findViewById(com.example.inmobiadstest.R.id.adIcon)
        var title: TextView? = view.findViewById(com.example.inmobiadstest.R.id.adTitle)
        var description:TextView? = view.findViewById(com.example.inmobiadstest.R.id.adDescription)
        var action: Button? = view.findViewById(com.example.inmobiadstest.R.id.adAction)
        var adContent: FrameLayout? = view.findViewById(com.example.inmobiadstest.R.id.adContent)
        var ratingBar: RatingBar? = view.findViewById(com.example.inmobiadstest.R.id.adRating)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val feedItem: Subject = subjectArray[position]
        if (holder is Viewholder){
            holder.subName.text = feedItem.subjectName
            feedItem?.subjectImage?.let {
                Picasso.get()
                        .load(it)
                        .into(holder.subImage)
            }
        }else if (holder is AdsViewHolder){
            val nativeAd: InMobiNative = (feedItem as AdFeedItem).mNativeStrand
            holder.title!!.text = nativeAd.adTitle
            holder.description!!.text = nativeAd.adDescription
            holder.action!!.text = nativeAd.adCtaText
            Picasso.get()
                    .load(nativeAd.adIconUrl)
                    .into(holder.icon);
            val rating: Float = nativeAd.adRating
            if (rating != 0f) {
                holder.ratingBar!!.rating = rating
            }
            holder.ratingBar!!.visibility = if (rating != 0f) View.VISIBLE else View.GONE
        }


    }

    override fun getItemViewType(position: Int): Int {
        val feedItem: Subject = subjectArray[position]
        return if (feedItem is AdFeedItem) {
            VIEW_TYPE_INMOBI_STRAND
        } else VIEW_TYPE_CONTENT_FEED
    }

}
