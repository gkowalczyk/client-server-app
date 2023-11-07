# client-server-app
Server application using sockets (without using ANY frameworks).

The server handle the following commands (and return responses in JSON):

"uptime" - returns the server's uptime.
"info" - returns the server's version number and creation date.
"help" - returns a list of available commands with a brief description (those commands you are currently reading about, in other words, the commands implemented by the server).
"stop" - simultaneously stops both the server and the client.
