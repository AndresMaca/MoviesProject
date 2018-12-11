# MoviesProject
Movie App that contains good practices, dagger, room, LiveData, ViewModels and A repo.
You can adapt this app to your project before you ask me.
The app can be costumized in several ways so im writing this --> (to help you adapt to your needs)
This project present an App that show movies in three categories, with a good defined architecture, the data is in the repo, the viewmodel is the middleman between data and the presentation layer(fragments in this case).
This app can be simplified in a very extensive ways(i.e: the three fragments works with the same principles its easy to made just one parent fragment and passed the type of fragment), but the idea is make a *deacoupling* system that allows mount a very high number of specific features asked by the POwner or ScrumMaster.
![architecture](final-architecture.png)
The general idea of main architecture is in the image, in this case i use some view models and not just one (ofc this can be achieved with 45m. or less of work its simply), and added observer pattern to ask data when the views are running out of data.

# Network
The App in first try to catch data from the network (it can be data friendly if you query online data only when youre running out of local data i make only first because i want to update things like popularity or votes) if cant reach the net, hits the local database and query some info.

# Data.
The dataflow is running with the following way:
  -You have a category and each cat. has n page (about 20 items, each item is the movieId), the repo ask for pages, ie. <br />
   - View Layer: Okay Im running out of new data, the user just scroll to the bottom. <br />
   - Model View: Oh man, dont worry i can help you with this just let me ask to a friend. Hey Repo can you some data to give me? Im current in the page number 2 of Top Rated Movies. <br />
   - Repository: All right bro, im just getting the page number 3 of Top Rated Movies. <br />
   - Model View: Perfect this is fresh data, im going to pass it to my View Layer buddy, Ey view!  is the new data you are looking for (look word is very important this is achieve with LiveData that triggers when new data is added, the M.V. add it and the View observe) <br />
   - View Layer: Perfect! Im going to *update* the view! <br />

# Repo
The repository is the responsible of query data from the network and the local database. It has executor to dont block ui thread 
# View Model
Connects the repo with the view layer, im using LiveData to shoot changes to the view.
# Views
Fragments that are observing for data changes of LiveData. When they need more data they talk with the ViewModel instead of the repo. Just for good practices.

# Project Folder Structure

You can read with more details in each class.
 - Activities contains the activities.
 - Adapter contains the recycler adapter. In this case I use heritage and override the get item view type method for differentiate the starred movies of the normal ones, between Popular Movies and Top Movies, you can implement the way you want just edit the content inside the if.
 - Api: The api interface. And the fields that help us for networks queries.
 - Database: local database related.
  - converter: Room needs some help with List of objects so, this class help us.
  - dao: Our Movie sentences to make sql interactions
  - entity: The objects that i use 
 - devutils: Forget me for this, i love make custom log debug 
- di : dependecy injection. So here is when dagger is configured. You must add your View Model in the ViewModelModule. And if you have fragments linked with activities ActivityModules is where you have to look.
- fragments: contains our fragments
- viewmodels: contains the view models and the viewmodelFactory -> checks that where going to use a viewmodel 
  
# FAQ.
  - En qué consiste el principio de responsabilidad única? Cuál es su propósito?
    Each class must have one and ONLY ONE task, so  is prohibited get data from the network directly of one of the view classes, this help not only to make readeable code but maintainable code, in this project I make it thinking in scalability.
  - Qué características tiene, según su opinión, un “buen” código o código limpio?
    The Clean code is auto - readable i mean you have to assign good names to your variables and methods, your code have to be clean and simple, the simple is elegant, Juniors devs may think that more is better (okay i recognize i made a lot of classes in this project but its so easy to simplify the project and i make it to made the project readable)  spaghetti code is the enemy.
    Before you code something think is really necessary? Making complex or trying to show off skills making a lot of useless code is a very big trouble you really spend low time writting and a lot reading code so make the life easy!
# Before you read this.
Im not going to teach you how dagger2 works there are hundred of several good quality post, so go ahead and read some before, TL/DR okay this is not a tutorial and im going to asume you knows some basics concepts:
Dagger2 allows you make *dependency injection* (yeah thats are the initials of the folder di) and nice *singletons* its like you dont need to instantiate your web services mutiple times instead make it just one.
Room allow you to make sql simplier but dont forget to make the queries in non user interface threads!


The running config is AndroidStudio 3.1.2
Osx Mojave 10.14.1
Android 8.0
# MADE WITH  ❤️ FROM COLOMBIA
