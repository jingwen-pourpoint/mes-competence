package com.example.mescompetences.repositories

import android.util.Log
import com.example.mescompetences.models.CompetenceModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.security.auth.callback.Callback

object CompetenceRepository {

    val dbRef = FirebaseDatabase.getInstance("https://mes-competences-f4c3b-default-rtdb.europe-west1.firebasedatabase.app/").getReference("competences")

    val competences = arrayListOf<CompetenceModel>()

    fun updateAll(callback: ()->Unit){
        dbRef.addValueEventListener(object: ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                competences.clear()
                snapshot.children.forEach {
                    val comp = it.getValue(CompetenceModel::class.java)
                    if(comp!=null) competences.add(comp)
                }
                callback()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("CompetenceRepository",error.message)
            }

        })
    }

    fun insert (competence: CompetenceModel): Unit {
        competence.lastModification = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        dbRef.child(competence.id).setValue(competence)
    }

    fun remove(competence: CompetenceModel): Unit {
        dbRef.child(competence.id).removeValue()
    }

}