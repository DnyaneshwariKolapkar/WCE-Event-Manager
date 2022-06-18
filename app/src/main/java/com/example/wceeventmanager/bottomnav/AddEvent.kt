package com.example.wceeventmanager.bottomnav

data class AddEvent (var eventName: String, var eventType: String, var eventDate: String, var startTime: String, var duration: String, var AOI: ArrayList<String>, var Branches: ArrayList<String>, var meetLink: String, var venue: String)