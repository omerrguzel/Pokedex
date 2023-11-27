<br/>
<p align="center">
    <img src="https://github.com/omerrguzel/Pokedex/assets/58399384/df81a134-ce03-4bd1-9a95-ed7180b83056" alt="Logo" width="80" height="80">
  </a>
  <h1 align="center">Pokédex</h1>
</p>

<p align="center">  
The Pokédex Android app, developed for a <a href="https://www.pazarama.com/">Pazarama</a> and <a href="https://coderspace.io/">Coderspace</a> bootcamp, incorporates essential technologies like AndroidX, Hilt, Coroutines, Retrofit, and Glide, ensuring a robust and efficient user experience.
</p>
</br>

<h2 align="center">Screenshots</h2>
<table align="center">
  <tr>
    <td align="center">
      <img src="https://github.com/omerrguzel/Pokedex/assets/58399384/f32a1c00-0b0b-495d-b2ef-ffff23f9ac8a" alt="Home Screen" width="250"/><br/>
      Home
    </td>
    <td align="center">
      <img src="https://github.com/omerrguzel/Pokedex/assets/58399384/53be2e6c-0224-49fd-a4fd-e72995e5e9ab" alt="Detail Screen" width="250"/><br/>
      Detail
    </td>
    <td align="center">
      <img src="https://github.com/omerrguzel/Pokedex/assets/58399384/5915b059-6bfc-4b41-95c2-da305159c4fb" alt="Search Screen" width="250"/><br/>
      Search
    </td>
    <td align="center">
      <img src="https://github.com/omerrguzel/Pokedex/assets/58399384/da6b38a7-d01a-48a9-9cb6-6ddd16a9e3b6" alt="Sort Screen" width="250"/><br/>
      Sort
    </td>
  </tr>
</table>
<br/><br/>


<h2 align="center">Tech stack & Open-source libraries</h2>

- Minimum SDK level 24
- [Kotlin](https://kotlinlang.org/) based, using [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for asynchronous programming.
- Jetpack
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding): Enables more robust and concise view interaction.
  - [Navigation](https://developer.android.com/guide/navigation): Manages app navigation within a single activity.
  - [Hilt](https://dagger.dev/hilt/): For dependency injection.
- Architecture
  - MVVM (Model-View-ViewModel) for clear separation of concerns.
- Networking
  - [Retrofit2](https://github.com/square/retrofit) & [Gson](https://github.com/google/gson): For REST API communication and JSON serialization/deserialization.
- [Glide](https://github.com/bumptech/glide): For image loading.
- [Android KTX](https://developer.android.com/kotlin/ktx): Kotlin extensions that optimize Jetpack and Android platform APIs.
- [Material Components](https://github.com/material-components/material-components-android): For modern, material design UI.
- Testing
  - [JUnit](https://junit.org/junit4/): Standard framework for unit testing.
  - [MockK](https://mockk.io/): Kotlin mocking library.
  - [Turbine](https://github.com/cashapp/turbine): Testing library for Kotlin Coroutines.
<br/><br/>

<h2 align="center">Architecture</h2>

<p align="center">
  <img src="https://github.com/omerrguzel/Pokedex/assets/58399384/a00c3408-eee0-44ea-b616-7f78dcfb9dca" alt="Architecture Diagram" width="70%"/>
</p>

- **Data Layer**: 
  - Communicates with the PokéApi REST API via Retrofit.
  - Handles API requests and responses.

- **Domain Layer**:
  - Contains UseCases that execute business logic.
  - Facilitated by Repositories to operate on data.
  - Uses Mappers to translate data between the Repository and UI models.

- **UI Layer**:
  - Consists of Activities and Fragments.
  - Interacts with the ViewModel to manage UI data and state.
  - Handles UI events and maintains a clean MVVM architecture, ensuring separation of concerns.
 
  

# License
```xml
MIT License

Developed by omerrguzel

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

```
