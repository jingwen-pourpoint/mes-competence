package com.example.mescompetences.popups

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mescompetences.R
import com.example.mescompetences.adapter.TagAdapter
import com.example.mescompetences.models.CompetenceModel
import com.example.mescompetences.repositories.CompetenceRepository
import com.google.firebase.database.collection.LLRBNode


class CompetenceDetailPopup(context: Context,val competence:CompetenceModel): Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_competence_detail)

        val nameView = findViewById<TextView>(R.id.competence_title)
        nameView.text = competence.name

        val descriptionView = findViewById<TextView>(R.id.description_field)
        descriptionView.text = competence.description

        val tagsView = findViewById<RecyclerView>(R.id.tags_recycler)
        tagsView.adapter = TagAdapter(competence.tags)

        val removeButton = findViewById<Button>(R.id.reove_button)
        removeButton.setOnClickListener {
            CompetenceRepository.remove(competence)
            dismiss()
        }


        val level = findViewById<TextView>(R.id.niveau)
        level.text= competence.niveau.toString()

        val btnLvldown = findViewById<ImageButton>(R.id.btn_lvl_down)
        val btnLvlUp = findViewById<ImageButton>(R.id.btn_lvl_up)

        btnLvldown.setOnClickListener {
            competence.niveau --
            CompetenceRepository.insert(competence)
            level.text= competence.niveau.toString()
            if (competence.niveau == 0){
                btnLvldown.setBackgroundColor(Color.parseColor("#AAAAAA"))
            } else {
                btnLvlUp.setBackgroundColor(Color.parseColor("#32FF34"))
            }
        }

        btnLvlUp.setOnClickListener {
            competence.niveau ++
            CompetenceRepository.insert(competence)
            level.text= competence.niveau.toString()
            if (competence.niveau == 20){
                btnLvlUp.setBackgroundColor(Color.parseColor("#AAAAAA"))
            } else {
                btnLvldown.setBackgroundColor(Color.parseColor("#FF0000"))
            }
        }



    }
}