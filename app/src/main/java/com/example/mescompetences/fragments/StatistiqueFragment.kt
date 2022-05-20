package com.example.mescompetences.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.mescompetences.R
import com.example.mescompetences.adapter.CompetenceAdapter
import com.example.mescompetences.repositories.CompetenceRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class StatistiqueFragment: Fragment(){

    private val repo = CompetenceRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater
            .inflate(R.layout.statistique,container,false)

        val compList = repo.competences

        compList.stream()
            .max(Comparator.comparing { it.niveau })
            .ifPresent {
                val topCompetenceRecycler = view.findViewById<RecyclerView>(R.id.topCompetence_recycler)
                topCompetenceRecycler.adapter = CompetenceAdapter(listOf(it), requireContext())
            }


        val niveauTotalView = view.findViewById<TextView>(R.id.niveau_total)
        val sum = compList.stream()
            .mapToInt { it.niveau }
            .sum()
        niveauTotalView.text = sum.toString()

        val latestCompetence = compList.stream()
            .max(Comparator.comparing { LocalDateTime.parse(it.lastModification, DateTimeFormatter.ISO_LOCAL_DATE_TIME) })
            .ifPresent {
                val latestCompetenceRecycler = view.findViewById<RecyclerView>(R.id.derniereCompetence)
                latestCompetenceRecycler.adapter = CompetenceAdapter(listOf(it), requireContext())
            }

        return view
    }
}