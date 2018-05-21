# Alexa-to-Discord

# Introduction
Alexa-to-Discord is a work in progress Alexa skill which allows users to interect with their discord guild(s) using an Alexa enabled device.

- - - -

# Table of Contents
 - [Features](#Features)
    - [Current Features](#Current-Features)
    - [Planned Features](#Planned-Features)
 - [Generating your Discord token](#Generating-your-Discord-token)
 - [Deploying the Application](#Deploying-the-Application)
    - [Deploying to Heroku](#Deploying-to-Heroku)
 - [Creating the Alexa Skill](#Creating-the-Alexa-Skill)
 
 - - - -
 
# <a name="Features"></a>Features

## <a name="Current-Features"></a>Current Features
Currently the skill has 6 intents you can:
 - Get all guilds the bot is connected to.
 - Select a specific guild.
 - Get the roles from the selected guild.
 - Get the text channels from the selected guild.
 - Get the voice channels from the selected guild.
 - Get all channels in the selected guild.
 
## <a name="Planned-Features"></a>Planned Features
These are features that we plan to implement when we have time or they are waiting for push notifications to be release for the echo.
 - Be able to select a specific channel in the selected guild.
 - Get the last x messages from the selected channel.
 - Get the active users in the selected channel.
 - Be able to send messages to the selected channel.
 - Allow alexa to speak when a message is sent to a specific channel. (Requires push notifications)

- - - -

# <a name="Getting-Started"></a>Getting Started
Firstly, you are going to want to make sure you have a [Discord account](https://discordapp.com/) and a [Amazon Developer account](https://developer.amazon.com/), although not necessary, I highly suggest getting a [Heroku account](https://dashboard.heroku.com/apps) as this will allow you to host your code for free without having to mess around with SSL certificated or HTTPS.
 
- - - -

### <a name="Generating-your-Discord-token"></a>Generating your Discord token
1. Go to the [discord developers portal](https://discordapp.com/developers) and log in with your discord account.
2. Click on the "My Apps" link in the menu on the left.
3. Click on "New App".
4. Give it a name, adding a description and icon is optional. Once you are done, press the "Create App" button.
5. After that you are going to want to click the "Create a bot user" button.
6. Reveal the bot token and store this somewhere for use later on.

- - - -

### <a name="Deploying-the-Application"></a>Deploying the Application
If you decide to not use [Heroku](https://dashboard.heroku.com/apps) you need to set an environment variable in the environment that the application will be running. The environment variable needs to be called "DISCORD_TOKEN" and must have a value of your discord bot token that you generated earlier.

#### <a name="Deploying-to-Heroku"></a>Deploying to Heroku
1. Fork this repository.
2. Navigate to [Heroku](https://dashboard.heroku.com/apps) and login with your account.
3. Create a new app and give it a name.
4. Navigate to the "Settings" tab.
5. Press the "Reveal config vars" button.
6. Add a new var with the key "DISCORD_TOKEN" (without the "") and set the value to the token you generated earlier.
7. Press the "add" button.
8. Under deploy settings select the Github option.
9. Login to your github account and allow Heroku access.
10. Select the forked repository in the "Connect to github" section.
11. Select the correct branch in the "Manual Deploy" section and press the "Deploy Branch" button.
12. Press the "Open App" button and take not the url it redirects you to you will need this when configuring the Alexa skill.

- - - -

### <a name="Creating-the-Alexa-Skill"></a>Creating the Alexa Skill
1. Go to the [Amazon developer portal](https://developer.amazon.com/) and login using your account.
2. Once logged in click the "Alexa" button towards the top of the page.
3. Then click the "getting started" button underneath Alexa Skills Kit.
4. Then click the "Add a new skill" button towards the top right of the page.
5. Leave the skill type as "Custom interaction model" then fill in the other fields too your liking. Set the invocation name to something memorable as this is what you will say to launch the skill.
6. Leave audio player option to no as currently this application does not use it.
7. Press the next button.
8. On this page you need to place the intent schema and sample utterances in their respective boxes. You can find these in the "SpeechAssets" directory of this repository.
9. Press the next button.
10. On this page you need to specify how Alexa will communicate with this application. I reccomend that you fork this repository and deploy it to [Heroku](https://dashboard.heroku.com/apps) directly from Github.
11. Leave the account linking option as no.
12. Press the next button.
13. Select the appropriate SSL certificate option, if you deploy to Heroku you can select "My development endpoint is a sub-domain of a domain that has a wildcard certificate from a certificate authority". Otherwise you will have to generate and provide valid signed SSL certificates for your endpoint.
14. Press the save button.

- - - -

# Todo: finish this
