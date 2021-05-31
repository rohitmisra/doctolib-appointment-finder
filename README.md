# doctolib-appointment-finder
![Docker Cloud Build Status](https://img.shields.io/docker/cloud/build/rohitmisra44/doctolib-appointment-finder?logo=docker&style=plastic)

Bot to find hard to find appointments(eg. Vaccination) on Doctlib and notify on Slack

## Requirements

- Docker
- Slack account (free personal also works)
- Bot in Slack account

## Installation

1. Download the docker-compose file:
    ```
    $wget https://github.com/rohitmisra/doctolib-appointment-finder/blob/master/docs/install/docker/docker-compose.yml 
    ```

2. Create an application config file in ./app-config/application.yml.
   (The template exists in the docs/install/docker directory) in this repo.
   ```
   $mkdir app-config && cd app-config
   $wget https://raw.githubusercontent.com/rohitmisra/doctolib-appointment-finder/master/docs/install/docker/app-config/application.yaml.template
   ```

3. Add appointments to watch for
   The following can be found by debugging the network on a browser while searching for
   appointments on the page of the praxis
   ```
   visit_motive_ids- the id of a motive at the practice
   agenda_ids- the id of the agenda at a practice
   practice_ids- the id of the practice
   path- Path of the practice booking page (relative to https://doctolib.de/<path>) to display on Slack
   ```
   config for the message on Slack
   ```
   tag- just a name to identify   
   message- message for the bot to show on Slack
   ```
   the visit_motive_id, agenda_id and practice_id 

4. Add the credentials for the Slack Bot
   - Create a slack bot
   - copy the bot name, auth token
   - create a channel on Slack
   - copy the id of the channel (not name!!)
5. The final config file should look like this:
   ```
   termin-finder:
   praxis:

   -  tag: some-practice-biontech
      visit_motive_ids: <visit_motive_id>
      agenda_ids: <agenda_id>
      practice_ids: <practice_id>
      path: path/to/appointment
      message: BioNTech-Pfizer at Some Practice

   -  tag: some-other-practice-janssen
      visit_motive_ids: <visit_motive_id>
      agenda_ids: <agenda_id>
      practice_ids: <practice_id>
      path: path/to/appointment
      message: Jansenn at Some Other Practice

   spring:
      main:
         web-application-type: none
   
   slack-bot:
      bot-config:
         bot-name: <slack-bot-name>
         auth-token: <slack-bot-auth-token>
         channel-name: <channel-id>
   ```
   
## Run
```
In the root directory:
$docker-compose up -d
```
## Demo
![Screenshot](docs/demo/slack-screenshot.png?raw=true "Title")

 
