//package dev.outup.coffeeapp.infrastructure
//
//import android.content.Context
//import android.content.Intent
//import android.view.LayoutInflater
//import dev.outup.coffeeapp.domain.model.Content
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import android.widget.ImageButton
//import androidx.recyclerview.widget.RecyclerView
//import com.google.firebase.ktx.Firebase
//import com.google.firebase.storage.ktx.storage
//import dev.outup.coffeeapp.MainActivity
//import dev.outup.coffeeapp.R
//
//class CustomAdapter(private val contents: ArrayList<Content>, private val applicationContext: Context) :
//    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
//    private val storage = Firebase.storage
//    private val ONE_MEGABYTE: Long = 1024 * 1024
//
//    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val contentPicture: ImageView
//        val title: TextView
//        val description: TextView
//        val fixButton: ImageButton
//        val postedDate: TextView
//
//        init {
//            contentPicture = view.findViewById(R.id.contentPicture)
//            title = view.findViewById(R.id.contentTitle)
//            description = view.findViewById(R.id.contentDiscription)
//            fixButton = view.findViewById(R.id.fixButton)
//            postedDate = view.findViewById(R.id.postedDate)
//        }
//    }
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.activity_content, viewGroup, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//        val content = contents[position]
//
//        viewHolder.fixButton.setOnClickListener {
//            val intent = Intent(applicationContext, MainActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            applicationContext.startActivity(intent)
//        }
//
//        viewHolder.title.text = content.title
//        viewHolder.description.text = content.coffee.toString()
//        viewHolder.postedDate.text = content.createdAt.toString()
//        if (content.imageLocation != null) {
//            val storageRef = storage.getReference(content.imageLocation)
//            storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener {
//                GlideApp.with(viewHolder.contentPicture)
//                    .load(storageRef)
//                    .centerCrop()
//                    .into(viewHolder.contentPicture)
//            }
//        }
//    }
//
//    override fun getItemCount() = contents.size
//}