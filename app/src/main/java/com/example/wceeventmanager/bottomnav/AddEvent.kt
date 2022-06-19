package com.example.wceeventmanager.bottomnav

import org.json.JSONArray

data class AddEvent (var eventName: String, var eventType: String, var eventDate: String, var startTime: String, var duration: String, var AOI: JSONArray, var Branches: JSONArray, var mode: String, var meetLink: String, var venue: String)