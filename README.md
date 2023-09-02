# Verifica - Email Verification API

Verifica is a simple REST API that can be used to validate emails or email domains. I built This simple application to get familiar with Springboot and play around with the variety of features Spring Boot has to offer. I hosted this microservice in Heroku until Heroku was free and also tried publishing it in Rapid API Hub as a free API.

## Description

This Rest API lets you syntactically validate email addresses and also check if 
The domain of the email address is valid by looking into MX Records.

## Getting Started

### Dependencies

* IDE (Intellij or Eclipse or Spring Tool Suite Recommended)
* Java Version 8 
* [Deta](https://web.deta.sh) Account

### Executing code locally

* Clone the repo using the below command
```
git clone https://github.com/nivethsaran/verifica.git
```

* Import project as maven project in your preferred IDE
* Create a run configuration in your preferred IDE. An example of IntelliJ Idea can be found [here](https://jetbrains.com/help/idea/run-debug-configuration.html).
* Add the below VM arguments to the run configuration
```
-Dspring.profiles.active=local
```
* Go to Deta Dashboard and create a new project.
* Copy the project ID and project key and save it securely.
* Add the project key and ID as environment variables in the run configuration.
```
DETA_SECRET_KEY=<DETA-SECRET-KEY>
DETA_VERIFICA_PROJECT_ID=<DETA-PROJECT-ID>
```
* Run/debug your Spring boot application and your API Server will start in a moment.


## Authors

Contributor details

* Niveth Saran (@nivethsaran)

## Version History

* 1.0
    * Initial Release

## License

This project is licensed under the [MIT License](https://github.com/nivethsaran/verifica/blob/main/LICENSE).
