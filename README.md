# PotholeCivilAuthorityAndroidApp
This is repository for Pothole Civil Authority Android app

This is one of the components of a complete Pothole App 

The complete understanding of Pothole app could be found over here []

Links to other components : 

Pothole Civil Authority Server
Pothole User Android App 
Pothole User Server 

Lambda 

This app is the frontend interface for Admin / Civil Authorities .

This app communicates with the Node js / Express backend server , and talks to it via set of APIs 

### Prequistes 

An account must be created for the Civil authority in database manually , specifying contact details and area that civil authority operates in GeoJSON format (no interface exists for that)

## Features of this app
1) View and track status of Potholes registered or reported within you locality.
2) Filter Potholes based on current status  (Resolved , Assigned  , Rejected , etc)
3) Merge multiple into one pothole , i.e. One Pothole could be reported multiple times , so Admin/Authority can merge them into one, Also in new updates , Potholes within same vicinity are merged automatically in the backend
4) One to Many Communication between Authority and User , Authority can update the Pothole status , so that user can get notified and track the status of Potholes too , Merged Potholes will notify all the users the users realted to that Pothole.
5) HeatMaps , to show the location with most occuring potholes , so that Authority can get an idea of which location suffers from more potholes
6) The app have notifications features and email updates too.
7) There are more technological details and security factors too , I won't go into implementation detail over here.

## Technologies Used

Android (Java) , Retrofit , Glide , etc.

## Screenshots


> There are few fixes that are needed to be done , so before using it into production make sure of some fixes.
