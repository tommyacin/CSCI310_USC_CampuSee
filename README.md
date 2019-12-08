# CSCI310_USC_CampuSee
# Chao Mein (Team #22)

Team Members:
- Tomas Acin-Chediex (5967613148)
- Glory Kanes (4780923011)
- Eric Zhan (3566990305)
- Ginger Dudley (6181464309)
- Stacy Phan (8926181032)

To run our app, you first need an API key from Google Cloud. Go to the Google
Cloud Platform console, select 'APIs and Services', then select 'Credentials', 
and create an API key. Copy this key into the appropriate location in
'app/Manifests/AndroidManifest.xml'. Also make sure to go to the API library
and enable 'Maps SDK for Android'.

Next, be sure to sync the project with the 'build.gradle'. To do this, click
'File' in the top-left corner of Android Studio and select 'Sync Project with
Gradle Files'.

Next, you need to set up an emulator for the appropriate device. We chose the
'Pixel XL API 29'.

Once everything is ready, simply click run. Once the application launches,
you're ready to go!


Improvements since 2.4:
- Users can now naviagte to their profile page and edit their personal information
- Publishers can also now naviagte to their profile page and edit their personal information
- Users can now click on a notification to see the full contents of the event
- Publishers can now edit events that they have already published
- The map markers now display the Publisher's name rather than their building
- The modal that pops up when Publishers click on their events has been tweaked to show more relevant information