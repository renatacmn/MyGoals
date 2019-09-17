Solution to the following assignment:

## Assignment

The test task involves writing a simplified version of the user's list of savings goals, the list that the user sees when opening the app. This list should be displayed as the provided image shows. Each goal has one entry in the list displaying the goals image, an optional target amount and how much has been saved. See provided image.

![List](https://i.imgur.com/MPAI5Hk.png)
![Details](https://i.imgur.com/oxXH7UM.png)

### Implementation

We provide you with a simplified API to get the list of goals to display and information about them. The API does not require any login. What we would like to see is:
- A project written ideally in Kotlin but we will accept Java as well
- some usage of RxJava
- some usage of Google Databinding
- Read/write data to a persistent storage/DB

How you implement this and what external libraries you use are entirely up to you.

The following API is available:

#### Endpoint

```http
http://qapital-ios-testtask.herokuapp.com
````

- /savingsgoals
- /savingsrules
- /savingsgoals/:id/feed

## The Project

- MVVM with ViewModel
- Room
- RxJava
- Dagger 2
- Retrofit/Moshi
- Unit tests with Mockito
