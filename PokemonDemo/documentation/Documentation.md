## Hello! Here is the Documentation

## API Documentation
The methods and endpoints can be found in our [Swagger](http://localhost:8080/swagger-ui/index.html) documentation.
Note: In order to access the swagger you must first launch the application. 

## Architecture of the project

The project structure follows MVC design principles and uses Maven as the dependency and build management system. This architecture was chosen to organize and separate application responsibilities in a cleaner and more modular way.
In the project structure the MVC is reflected in the following way:

- Model: [Entity](https://github.com/Catadecasm/Pokemon-World/tree/develop/PokemonDemo/src/main/java/com/example/pokemondemo/entity) and [Repository](https://github.com/Catadecasm/Pokemon-World/tree/develop/PokemonDemo/src/main/java/com/example/pokemondemo/repository)
- View: Endpoints
- Controller: [Service](https://github.com/Catadecasm/Pokemon-World/tree/develop/PokemonDemo/src/main/java/com/example/pokemondemo/service)

The base classes with which we developed this project can be found in our [ER diagram](https://github.com/Catadecasm/Pokemon-World/blob/develop/PokemonDemo/documentation/Diagram.png).

## Epics and features
#### Epics:

-  [POK-1 Authentication](https://atillachakarov.atlassian.net/browse/POK-1?atlOrigin=eyJpIjoiOWIwMmUzZjVmM2FiNDM2YWE4ZTJiN2M5ZmE3NTNmNTgiLCJwIjoiaiJ9)
- [POK-5 Profiles](https://atillachakarov.atlassian.net/browse/POK-5?atlOrigin=eyJpIjoiMGM4YmJjNzFjY2ZlNDhiY2JiMDQyZmUzMDNlOGQzN2YiLCJwIjoiaiJ9)
- [POK-11 My Pokémons](https://atillachakarov.atlassian.net/browse/POK-11?atlOrigin=eyJpIjoiZDAyZjdkODRlZjQzNDVmYWFlYTE3OWY1MjBhOWI3MzYiLCJwIjoiaiJ9)
- [POK-16 Pokedex](https://atillachakarov.atlassian.net/browse/POK-16?atlOrigin=eyJpIjoiMWY4MTY1OTQxMTQzNGRhZmE5ZjU4ZjEzYmE3NjRiZGMiLCJwIjoiaiJ9)
- [POK-20 Fights](https://atillachakarov.atlassian.net/browse/POK-20?atlOrigin=eyJpIjoiYTk5MTk5YmM4MzdjNDE5NTlmZmQwMmFhMTgwM2EzYzgiLCJwIjoiaiJ9)
#### Features for each epic:

#####  POK-1 Authentication
| ID     | Name           | Description                                                                                                                                                                                                    |
|--------|----------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [POK-29](https://atillachakarov.atlassian.net/browse/POK-29?atlOrigin=eyJpIjoiNGE5YWJhMDU1NDc0NGJlMzhjYzYxMjE4MjU1MmUxMzAiLCJwIjoiaiJ9) | JWT auth       | As a system administrator, I want to implement JSON Web Token (JWT) authentication,So that we can enhance the security of our application and provide a more robust and standardized authentication mechanism. |
| [POK-34](https://atillachakarov.atlassian.net/browse/POK-34?atlOrigin=eyJpIjoiNDRjYTllOWZiZGU2NDM4Nzk1YzgwYzVkMDZjOGIyZjgiLCJwIjoiaiJ9) | Validate Role  | As a system user, I want to ensure that the role chosen during the sign-up process is valid, So that only authorized roles (trainer, oak, doctor, administrator) are allowed to register in the application.   |
| [POK-2](https://atillachakarov.atlassian.net/browse/POK-2?atlOrigin=eyJpIjoiMGI1MzlkNWFmOTM3NDcxZGJhZmMwNTQ5MmJjNDJiZTciLCJwIjoiaiJ9)  | Sign Up a User | As a Trainer, I want to  be able to register in order to sign up for the platform So, I can use the different functionalities of the application.                                                              |
| [POK-3](https://atillachakarov.atlassian.net/browse/POK-3?atlOrigin=eyJpIjoiMTQ5NDFmMWFjNjVjNGI4NTlkYjVhYzE4NzVhNjdmMDYiLCJwIjoiaiJ9)  | Log in a User  | As a Trainer, I want to  be able to register in order to log in to the platform So, I can use the different functionalities of the application.                                                                |
| [POK-4](https://atillachakarov.atlassian.net/browse/POK-4?atlOrigin=eyJpIjoiOTcxMWY4ZWVlZWJmNDZhN2E5YmYyNDUzMjZlMGNlYzQiLCJwIjoiaiJ9)  | Log out a User | As a Trainer, I want to  log out of the system So, I can finish my session on the Pokedex application.                                                                                                         |

#####  POK-5 Profiles
| ID     | Name                                | Description                                                                                                                                                       |
|--------|-------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [POK-6](https://atillachakarov.atlassian.net/browse/POK-6?atlOrigin=eyJpIjoiMjZlYmY2YjkxOGFkNDNlZGJmZTQ5Njk5NDI5ZTY5OTQiLCJwIjoiaiJ9)  | Follow & unfollow another Trainer   | As an Administrator, I want to grant access to a pokémon trainer to be professor Oak, doctor or administrator So,  I can change the role for a specific username. |
| [POK-7](https://atillachakarov.atlassian.net/browse/POK-7?atlOrigin=eyJpIjoiOTgxZDcxOGM5MWRlNGM2MWE3ZWM5ZjM1NGE5YTM2YzQiLCJwIjoiaiJ9)  | Watch all pokémons from a Trainer   | As a Trainer, I want to see the pokémon of another trainer So, I can obtain the pokémon list of other trainers if I follow them.                                  |
| [POK-8](https://atillachakarov.atlassian.net/browse/POK-8?atlOrigin=eyJpIjoiYjI1YWU4NDY1MTcwNDY3Y2E1YTVjOTYxNDhhZjY1ODkiLCJwIjoiaiJ9)  | Watch all pokémons as Professor Oak | As a Professor Oak, I want to see all pokémons of a pokémon trainer So,  I can see the list of pokémon's user.                                                    |
| [POK-9](https://atillachakarov.atlassian.net/browse/POK-9?atlOrigin=eyJpIjoiNDY1MTlmZDI3MjgyNGU2ZmIxNmU1N2IxYzUxY2YzODYiLCJwIjoiaiJ9)  | Cure pokémons as pokémons doctor    | As a  pokémon doctor, I want to cure a pokémon of a pokémon trainer So, I can restore health of an specific pokémon of a pokémon trainer.                         |
| [POK-10](https://atillachakarov.atlassian.net/browse/POK-10?atlOrigin=eyJpIjoiYmQ1MWIxNGY3ZjVmNDMwNTlmNGM1YTczYzE4YjYwMzAiLCJwIjoiaiJ9) | Administrate profiles               | As an Administrator, I want to grant access to a pokémon trainer to be professor Oak, doctor or administrator So,  I can change the role for a specific username  |

#####  POK-11 My Pokémons
| ID     | Name                                         | Description                                                                                                                                   |
|--------|----------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------|
| [POK-13](https://atillachakarov.atlassian.net/browse/POK-13?atlOrigin=eyJpIjoiNzU0M2U5ODkwMTIxNDBkOWJkYzMzOWMzMDU2YjliNTgiLCJwIjoiaiJ9) | View my pokemon list                         | As a Trainer, I want to have an enquiry of all the pokémon that I catch So, I can see, filter, paginate and sort all the pokémon that I have. |
| [POK-15](https://atillachakarov.atlassian.net/browse/POK-15?atlOrigin=eyJpIjoiYTRkYTZlOTBhYjMzNGIyYWFlMWM4ZDJmNDc3MzUyZDUiLCJwIjoiaiJ9) | Delete a pokemon in my pokedex               | As a Trainer, I want to be able to remove my pokémon So, I cannot see it any longer in my pokedex.                                            |
| [POK-14](https://atillachakarov.atlassian.net/browse/POK-14?atlOrigin=eyJpIjoiOTZhY2E3OTgzMDAyNDBlZWI3YThhMWVjNTQ0YzZiM2MiLCJwIjoiaiJ9)| Update the data of any pokemon on my pokedex | As a Trainer, I want to be able to edit the information of my pokémon So, I can )actualize any status of information about my pokémon.         |
| [POK-12](https://atillachakarov.atlassian.net/browse/POK-12?atlOrigin=eyJpIjoiZjgxNWNkOGQ1MzhmNDBiMTkyNDU4ZGMzMTQyMzQ5ODQiLCJwIjoiaiJ9) | Add a pokémon to the trainer pokedex         | As a Trainer, I want to add my recently catch pokémon and name it So, I can have it in my poke Dex.                                           |

#####  POK-16 Pokedex
| ID     | Name                                   | Description                                                                                                                                          |
|--------|----------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------|
| [POK-31](https://atillachakarov.atlassian.net/browse/POK-12?atlOrigin=eyJpIjoiZjgxNWNkOGQ1MzhmNDBiMTkyNDU4ZGMzMTQyMzQ5ODQiLCJwIjoiaiJ9) | Establish connection to pokeapi        | As a Admin, I want to establish a connection to the PokeAPI, So that I can retrieve Pokemon-related data to enhance the features of our application. |
| [POK-17](https://atillachakarov.atlassian.net/browse/POK-17?atlOrigin=eyJpIjoiZDRlYmZiYjUyODA5NGE3N2IzMzJkNDNjNWI4NzExM2EiLCJwIjoiaiJ9) | View detailed pokemon                  | As a trainer, I want to see all the pokémons availables in the world So, I can know which one I need to catch.                                       |
| [POK-19](https://atillachakarov.atlassian.net/browse/POK-19?atlOrigin=eyJpIjoiYWQ3Zjk3MDBmZTVjNDIyNjk4ZGQ4YjJlNGI3YWI4NWEiLCJwIjoiaiJ9) | View the evolution path of any pokemon | As a trainer, I want to know what will be the evolution possibilities of a single pokémon So, that I can make the best decisions.                    |

#####  POK-20 Fights
| ID     | Name                                  | Description                                                                                                                                                                                                                              |
|--------|---------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [POK-21](https://atillachakarov.atlassian.net/browse/POK-21?atlOrigin=eyJpIjoiZjQ1Y2MyMmVjMGVhNDZkZTg2OTk1YjA4ZmQ3ZTlkMjgiLCJwIjoiaiJ9) | Update the mechanisms for any pokemon | As a Trainer, I want to  update each of my different fighting mechanisms for each of the pokémon So,  I can show them in my profile.                                                                                                     |
| [POK-22](https://atillachakarov.atlassian.net/browse/POK-22?atlOrigin=eyJpIjoiZTVmNzIxZWVlYWVlNDRjMWIxMjg4YzZmM2JiNjdkYTIiLCJwIjoiaiJ9) | Register in a league                  | As a Trainer, I want to  be able to register for different existing pokémon leagues, So,  I can fight in different leagues.                                                                                                              |
| [POK-23](https://atillachakarov.atlassian.net/browse/POK-23?atlOrigin=eyJpIjoiOGU0ZGI5ZTVjN2IzNDdiNzg4YzYwN2VjMGY1ZDA3YzgiLCJwIjoiaiJ9) | Win medals for my fights              | As a Trainer, I want to  be able to earn pokémon medals every time I win a battle So,  I can show them in my profile.                                                                                                                    |
| [POK-24](https://atillachakarov.atlassian.net/browse/POK-24?atlOrigin=eyJpIjoiZmI5NGJlOTUyMmQyNDc4NjlhYWI0OGExMGM1Njc0MjUiLCJwIjoiaiJ9) | Follow another trainer                | As a Trainer, I want to  follow the profiles of the other Trainers So,  I'll be able to visualize their activity and fight history.                                                                                                      |
| [POK-25](https://atillachakarov.atlassian.net/browse/POK-25?atlOrigin=eyJpIjoiNTc3ODZhOTg0N2Y0NGZhMzhjZGVhOWNkZWY2YWZjOTgiLCJwIjoiaiJ9) | Register pokemon fights               | As a Trainer, I want to be able to register a battle with another fighter So,  I'll be able to include it in my historical fight data.                                                                                                   |
| [POK-26](https://atillachakarov.atlassian.net/browse/POK-26?atlOrigin=eyJpIjoiZjZkYzM3MTEyM2QxNDU1NWIzZjg2Mzg1NDc2YTE0MWEiLCJwIjoiaiJ9) | Know all my fights                    | As a Trainer, I want to get in a paginated way each of the different fights that I have had listing from the most recent to the oldest one So, this way I will be able to visualize it better and dynamically as a business requirement. |

## Gitflow

## License

[MIT License](https://github.com/Catadecasm/Pokemon-World/blob/develop/LICENSE)

## Useful Links
- https://pokeapi.co/api/v2/
