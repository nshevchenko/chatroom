$(document).ready(function() {
  console.log('==[ INITIATING AJAX CHAT INTERFACE ]==');
  // Focus on the input field
  $('.input-form-element').focus();
  $('body').scrollTop(1E10);
});

/* Listener for submission aka. when the user hits enter */
$('.input-form').submit(function(e) {

  // Save the value in the input field and clear the field
  var input = $('.input-form-element').val();
  $('.input-form-element').val('');

  console.log("USER INPUT: " + input);

  // Check if the input starts with a '/', if so it's a command
  if(input.indexOf('/') == 0) {
    console.log('COMMAND DETECETED');

    // Srip the '/' and run through the different available commands
    switch(input.substring(1).split(' ')[0]) {
      case 'help':
        cmdHelp(input);
        break;
      case 'login':
        cmdLogin(input);
        break;
      case 'register':
        cmdRegister(input);
        break;

    }
  // If it doesn't start with a '/', than it's a chat message
  } else {
    console.log('CHAT MESSAGE DETECTED');
    console.log('Sending POST to /auth');

    postMessage(input);

  }

  return false;
});

/* Called when the user types /help */
function cmdHelp(input) {
  input = input.substring(1).split(' ');

  var help     = "- /help - shows you this listing<br/>";
  var login    = "- /login [username] [password] - to login to the chat<br/>";
  var register = "- /register [username] [password] - to register<br/>";

  var response = "";

  if(!input[1]) {
    response = "Here are the available commands:<br/>"
      + help
      + login
      + register;
  } else {
    switch(input[1]) {
      case 'login':
        response = login;
        break;
      case 'register':
        response = register;
        break;
    }
  }
  addToChat('',response,true);
}

/* Called when /login [username] [password] */
function cmdLogin(input) {
  input = input.substring(1).split(' ');
  // Check that username and password was supplied
  if(input[1] && input[2]) {
    var response = "/login "+input[1]+" &#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;";
    var post_login = postLogin(input[1], input[2]);
    addToChat('',response,false);
  } else {
    // if not show the help for /login
    input = '/help login';
    cmdHelp(input);
  }
}

/* Called when /register */
function cmdRegister(input) {
  input = input.substring(1).split(' ');
  // Check that username and password was supplied
  if(input[1] && input[2]) {
    var response = "/register "+input[1]+" &#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;";
    addToChat('',response,false);
  } else {
    // if not show the help for /login
    input = '/help register';
    cmdHelp(input);
  }
}

/* Takes an author, string, and boolean for server-message and posts it to
the chat-container */
function addToChat(author, message, id) {
  var msgContainer;

  if(author.length > 0) {
    msgContainer     = '<div class="chat-message-container" data-id="'+id+'">\n';
    msgContainer    += '  <span class="chat-author">'+author+':</span>\n';
    msgContainer    += '  <span class="chat-message">'+message+'</span>\n';
  } else {
    msgContainer = '<div class="chat-message-container">\n';
    msgContainer    += '  <span class="chat-message server-message">'+message+'</span>\n';
  }
  msgContainer    += '</div>\n';

  $('.chat-container').append(msgContainer);
  $('body').scrollTop(1E10);
}

function postMessage(input) {
  console.log(input);
}

function postLogin(username, password) {
  $.post( "/wildfly-helloworld-mdb/auth", { username: username, password: password })
      .done(function( data ) {
        console.log('Response from login post: ' + data);
      });
}

function addToUsers(user) {
  // Add the username to the list of users
  $('.users-container ul').append('<li>'+user+'</li>');

  // Increment the counter of online users

  var users_online = $('.users-online').text();
  users_online = parseInt(users_online);
  users_online++;
  $('.users-online').text(users_online.toString());

};

function removeUser(user) {
  $('.users-container ul li').each(function(index) {
    if($(this).text() == user) {
      this.remove();
    }
  });
}
