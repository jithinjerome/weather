# Weather API Project 

## Overview

The **Weather API Project** is a Spring Boot application that fetches and provides weather information for a given city using the **Visual Crossing Weather API**. The application incorporates caching using **Redis** to improve performance and reduce API call overhead. Additionally, it handles errors gracefully and supports a clean, structured data response.

---

## Features

1. Fetches current weather details for any given city.
2. Caches weather data in **Redis** to reduce repetitive external API calls.
3. Provides detailed weather information including:
   - Resolved city name
   - Temperature (in °C)
   - Weather conditions
   - Feels-like temperature (in °C)
4. Handles invalid input or unavailable data with descriptive error messages.

---

## Technologies Used

- **Java**: Core programming language.
- **Spring Boot**: Framework for building the RESTful API.
- **Redis**: In-memory database used for caching.
- **Visual Crossing API**: Third-party weather API to fetch weather data.
- **Lombok**: For reducing boilerplate code (e.g., getters, setters).
- **Jackson**: For JSON parsing.
- **Maven**: Dependency management and build tool.

---

## API Endpoints

### 1. **Get Weather for a City**
   - **Endpoint**: `GET /api/weather`
   - **Query Parameter**:
     - `city` (String, required): Name of the city.

---

## Redis in This Project

### What is Redis?
**Redis** is an open-source, in-memory data store that is widely used for caching, real-time analytics, session storage, and more. It is highly performant and supports fast read/write operations.

### Why Use Redis in This Project?
- **Caching**: Weather data for a city is stored in Redis for a specified duration (`cache.expiration`). This reduces repetitive API calls to Visual Crossing and speeds up response time.
- **Performance**: Redis helps improve performance by serving cached data for repeated requests within the cache expiration time.

### How Redis Works in This Project
1. On a request, the API first checks Redis for cached weather data for the city.
2. If found, the cached data is returned immediately.
3. If not found, the Visual Crossing API is called, and the response is stored in Redis for future use.

---

## Visual Crossing Weather API

### What is Visual Crossing?
**Visual Crossing** is a powerful weather API that provides real-time weather data, historical weather records, and forecasts.

### How is Visual Crossing Used in This Project?
- **Endpoint**:
  The application uses the `/timeline` endpoint of Visual Crossing API to fetch weather data for a specific city.
- **Request URL**:
  ```text
  https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/{city}?key={apiKey}&unitGroup=metric
  ```
  The `unitGroup=metric` ensures the temperature data is returned in Celsius.

- **Data Extracted**:
  - `address`: Resolved city name.
  - `temp`: Temperature.
  - `conditions`: Weather conditions.
  - `feelslike`: Feels-like temperature.

- **Error Handling**:
  If the API returns an error (e.g., invalid city or unavailable service), the application provides an appropriate error message to the client.

---

## Example Workflow

1. **First Request**:
   - User requests weather for a city (e.g., `Berlin`).
   - Data is fetched from Visual Crossing API and cached in Redis.
   - Response is returned to the user.

2. **Subsequent Requests**:
   - If the same city is requested within the cache expiration time, data is served from Redis, avoiding external API calls.

## Contact
For any inquiries or feedback, please contact:
- **Jithin Jerome**  
- jithinsjerome@gmail.com
