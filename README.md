# MoviesProject
Movie App that contains good practices, dagger, room, LiveData, ViewModels and A repo.
You can adapt this app to your project before you ask me.
The app can be costumized in several ways so im writing this --> (to help you adapt to your needs)
This project present an App that show movies in three categories, with a good defined architecture, the data is in the repo, the viewmodel is the middleman between data and the presentation layer(fragments in this case).
This app can be simplified in a very extensive ways(i.e: the three fragments works with the same principles its easy to made just one parent fragment and passed the type of fragment), but the idea is make a *deacoupling* system that allows mount a very high number of specific features asked by the POwner or ScrumMaster.

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
The
# View Model
# Views
# Before you read this.
Im not going to teach you how dagger2 works there are hundred of several good quality post, so go ahead and read some before, TL/DR okay this is not a tutorial and im going to asume you knows some basics concepts:
Dagger2 allows you make *dependency injection* (yeah thats are the initials of the folder di) and nice *singletons* its like you dont need to instantiate your web services mutiple times instead make it just one.
Room allow you to make sql simplier but dont forget to make the queries in non user interface threads!

The running config is in AndroidStudio 3.1.2

# MADE WITH  ❤️ FROM COLOMBIA
