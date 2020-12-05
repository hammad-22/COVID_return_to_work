# COVID_return_to_work

Name: Beatrix Tran, Hammad Shahid, Kaleb Dagne

Group number: 46

COVID-19 Return to Work app.

**Application concept**

   The application purpose is to help users to monitor their general health, tracking COVID related information, and suggest appropriate action based on user current health and situations.

**Key functionalities**

   The users should be able to see general COVID information including medications, news, updates, and cases. They also have the ability to register/login to start their daily covid health check or check their health history. The COVID Check feature will enable users to check against the list of COVID symptoms, store the result in history, and have appropriate suggestions based on the result. Depending on the COVID check results, the suggestion feature will give the user the list of suggestions for appropriate actions.

**Architecture and who do what**
- **Register/Login**: (Default) - Beatrix ----- done
    - **Register page**:  unique username, unique email, password, age, gender - Beatrix ----- done
    - **Login page**: username, password - Beatrix ----- done

- **Home page**: Contain general information - Hammad
    
- **Bottom Navigation Bar**: Home, User History, COVID Check In, Logout - Beatrix ----- done

- **History Page**: Contain listview of user COVID check in results starting with the current result - Beatrix

- **Suggestion page**: Additional questionnaires to decide the appropriate action should be taken by the user (what to do if they sick and/or people in their house is sick) -  Kaleb 

- **COVID Check In**:
    - List (checkbox) of COVID symptoms for user to select (1) - Kaleb ----- done 
    - List of questionnaires related to user current living/working environment (2) - Hammad
    - From (1), (2), determine what categories the person fit: no symptoms, quarantine, critical. If the result is critical, send the user to suggestion page - Kaleb
    - Store the result in database -- Beatrix

**Android Components**

   Button, Relative Layout, Bottom Navigation Bar, Listview, Fragments, Checkboxes

**Resources** 

   We use Firebase to store user history and information.
