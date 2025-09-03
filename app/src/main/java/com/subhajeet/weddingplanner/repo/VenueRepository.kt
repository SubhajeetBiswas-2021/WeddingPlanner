package com.subhajeet.weddingplanner.repo

import com.subhajeet.weddingplanner.model.Venue
import javax.inject.Inject

class VenueRepository @Inject constructor() {

    fun getVenues():List<Venue>{
        return  listOf(
            Venue("Grand Palace", "Kolkata", "₹2,00,000 - ₹5,00,000", 300),
            Venue("Royal Banquet Hall", "Mumbai", "₹1,50,000 - ₹4,00,000", 250),
            Venue("Sunshine Gardens", "Bangalore", "₹3,00,000 - ₹6,00,000", 400),
            Venue("Elegant Event Center", "Delhi", "₹2,50,000 - ₹5,50,000", 350),
            Venue("Paradise Lawn", "Chennai", "₹1,00,000 - ₹3,50,000", 200),
            Venue("Crystal Palace", "Pune", "₹2,00,000 - ₹4,50,000", 300)
        )
    }
}