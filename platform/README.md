Platform
========


POST /parking/state     

    input => {
               parkingId:UUID,
               state:Boolean
             }  
    output => {}

GET  /parking/list

    input  => {}
    output => [
              {
                parkingId:UUID,
                parkingLocation: {
                  lat:Double,
                  long:Double
                }
              },
               ...
             ]

POST /sponsor/discount

    input  => {
                sponsorId:UUID,
                parkingIds: [UUID],
                discountRate: Double,
                discountedIntervals: [Interval]
              }
    output => {}

GET /consumer/listNearbyParkings      

    input => {
               userLatitude:Double,
               userLongitude:Double,
               googleDestinationQuery: String,
               expectedParkingDuration:Long
             }
    output => [
                {
                  parkingId: UUID,
                  parkingLocation: {
                    lat: Double,
                    long: Double
                  },
                  available: Boolean,
                  pricingInfo: {
                    price: Double,
                    ttl: Long ,
                    specialOffer: String
                  }
                },
                  ...
              ]

POST /regulation  

    input => {
               parkingIds: [UUID],
               unavailabilityIntervals: [Interval]
             }
    output=> {}



Parking States
===============

parking_id:UUUID | geo_point :LatLong | base_price_per_hour: double | availability: boolean


Regulations
===========
parking_id: UUID | ...


Discounts
=========

parking_id: UUID | ...
