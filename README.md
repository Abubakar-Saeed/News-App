# News App

The News App retrieves and displays the latest news articles from various categories using Retrofit for API calls. Built with Jetpack Compose for the UI and following the MVVM (Model-View-ViewModel) architecture, the app ensures clean separation of concerns and efficient state management. Users can browse news across categories like business, science, health, and more.

## Features

- Retrieve and display news articles using Retrofit API
- Browse news by categories (e.g., business, science, health, entertainment)
- Responsive and clean UI with Jetpack Compose
- MVVM architecture for better code organization and scalability
- Real-time data updates using ViewModel, LiveData/StateFlow
- Kotlin for development

## Architecture

This app follows the **MVVM (Model-View-ViewModel)** pattern:
- **Model:** Handles API data fetching using Retrofit.
- **View:** Jetpack Compose is used to build the UI and display news articles.
- **ViewModel:** Manages news data and business logic, utilizing LiveData/StateFlow for real-time updates.

## Technologies Used

- **Retrofit** - HTTP client for making API requests to fetch news articles.
- **Jetpack Compose** - UI toolkit for building native Android UIs.
- **MVVM Architecture** - For clean separation of concerns.
- **LiveData/StateFlow** - For observing data changes and real-time UI updates.
- **Kotlin** - Language used for development.

## Installation

1. Clone this repository:
    ```bash
    git clone https://github.com/yourusername/news-app.git
    ```
2. Open the project in Android Studio.
3. Build and run the app on an emulator or a physical device.

## Usage

- **Select Category:** Choose a news category from the provided options.
- **Browse News:** View the latest articles from the selected category.
- **Read Full Article:** Tap on an article to view its details.

![Screenshot_20240908_162316](https://github.com/user-attachments/assets/5483fe02-7218-4518-94ff-47522947f752)
![Screenshot_20240908_162406](https://github.com/user-attachments/assets/ea33dad4-041c-45fe-9ff8-af24bf2e5b9b)

