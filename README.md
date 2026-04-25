# Online Shop Application

An Android-based online shopping application built with Java.

This training project provides core e-commerce features such as browsing products, viewing categories, managing a shopping cart, using maps, offline database storage, shared preferences, and multiple UI screens for an integrated shopping experience.

## ✨ Features
- Product listing and category browsing
- Shopping cart functionality
- Product detail pages
- Image slider for banners
- Local database using SQLite
- SharedPreferences support
- Internet broadcast receiver
- Google Maps integration
- **Multiple UI pages:** Home, About Us, Cart, Category, Contact Us, Login / Register

## 📂 Project Structure
```
OnlineShopApplication-master/
├── app/
│   ├── src/main/java/com/example/digicala/
│   │   ├── MainActivity.java
│   │   ├── adaptor/
│   │   ├── broadcast/
│   │   ├── database/
│   │   ├── entity/
│   │   ├── gps/
│   │   ├── sharedpreferences/
│   │   └── ui/
│   ├── src/main/res/
│   └── AndroidManifest.xml
├── build.gradle
├── settings.gradle
└── gradlew
```
## 📦 Key Packages
- **`adaptor`**: Adapters for RecyclerView lists, download lists, and image sliders.
- **`broadcast`**: Contains internet connectivity broadcast receiver.
- **`database`**: SQLite database helpers for product data management.
- **`entity`**: Data models, including `Product`, `BookProduct`, and `ImageCategory`.
- **`gps`**: Google Maps related activity.
- **`sharedpreferences`**: Utility for saving lightweight user data.
- **`ui`**: Contains all UI screens such as product pages, cart, category screens, about us, login/register, etc.

## 🛠️ Technologies Used
- Java
- Android SDK
- SQLite
- RecyclerView
- SharedPreferences
- BroadcastReceiver
- Google Maps API
- Android Views & Layouts

## 📋 Requirements
- **Android Studio** (Arctic Fox or newer recommended)
- **JDK 8+**
- **Android SDK**
- **Gradle**

## 🚀 How to Run
1. Download or clone the repository.
2. Open the project in Android Studio.
3. Wait for Gradle sync to finish.
4. Ensure required Android SDK components are installed.
5. Run the application on a virtual or physical Android device.
