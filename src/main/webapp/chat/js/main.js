$(document).ready(function() {
  console.log('==[ INITIATING AJAX CHAT INTERFACE ]==');
  // Focus on the input field
  showWelcome();
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
      case 'logout':
          cmdLogout(input);
          break;
      case 'register':
          cmdRegister(input);
          break;

    }
  // If it doesn't start with a '/', than it's a chat message
  } else {
    console.log('CHAT MESSAGE DETECTED');
    console.log('Sending POST to /message');
    postMessage(input);
  }

  return false;
});

/* Called when the user types /help */
function cmdHelp(input) {
  input = input.substring(1).split(' ');

    var help     = "- /help - shows you this listing<br/>";
    var login    = "- /login [username] [password] - to login to the chat<br/>";
    var logout   = "- /logout - to disconnect from the chat<br/>";
    var register = "- /register [username] [password] - to register<br/>";
    var privacy  = "- /privacy {selective,everyone,noone} - choose who can see that you're online<br/>"
                 + "  /private selective";

  var response = "";

  if(!input[1]) {
    response = "Here are the available commands:<br/>"
      + help
      + login
      + logout
      + register
      + privacy;
  } else {
    switch(input[1]) {
        case 'login':
            response = login;
            break;
        case 'register':
            response = register;
            break;
        case 'logout':
            response = logout;
            break;
        case 'privacy':
            response = privacy;
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
    addToChat('',response,false);
    var post_login = postLogin(input[1], input[2]);
    if(post_login) {
      addToChat('','Login successfull',false);
      initiateChat(input[1]);
    } else {
      addToChat('','Login failed, try again',false);
    }
  } else {
    // if not show the help for /login
    input = '/help login';
    cmdHelp(input);
  }
}

function postLogin(username, password) {
  $.post( "/wildfly-helloworld-mdb/auth", JSON.stringify({ "username": username, "password": password }))
      .done(function( data ) {
        console.log('Response from auth login post: ' + data.toString());
      },"json");

    // dev only until response is parsed
    return true;
}

function cmdLogout(input) {
    input = input.substring(1).split(' ');
    // Check that username and password was supplied
    var response = "/logout";
    addToChat('',response,false);
    var post_logout = postLogout();
    if(post_logout) {
        addToChat('','Logout successfull',false);
        ceaseChat();
    } else {
        addToChat('','Logout failed, try again',false);
    }
}

function postLogout() {
    $.post( "/wildfly-helloworld-mdb/auth?logout")
        .done(function( data ) {
            console.log('Response from auth logout post: ' + data);
        });

    // dev only until response is parsed
    return true;
}

/* Called when /register */
function cmdRegister(input) {
  input = input.substring(1).split(' ');
  // Check that username and password was supplied
  if(input[1] && input[2]) {
      var response = "/register "+input[1]+" &#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;";
      addToChat('',response,false);
      var post_register = postRegister(input[1], input[2]);
      if(post_register) {
          addToChat('','Registration successfull! Try to login :)',false);
      } else {
          addToChat('','Registration failed! Try another username.',false);
      }
  } else {
    // if not show the help for /login
    input = '/help register';
    cmdHelp(input);
  }
}

function postRegister(username, password) {
  $.post( "/wildfly-helloworld-mdb/register", JSON.stringify({ "username": username, "password": password }))
      .done(function( data ) {
        console.log('Response from register post: ' + data);
      });

    // dev until parse response
    return true;
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

function postMessage(message) {
  $.post( "/wildfly-helloworld-mdb/message", { message: message })
      .done(function( data ) {
        console.log('Response from message post: ' + data);
      });
}

/*
* Adding a user to the list of users
* */
function addToUsers(user) {
  // Add the username to the list of users
  $('.users-container ul').append('<li>'+user+'</li>');

  // Increment the counter of online users
  var users_online = $('.users-online').text();
  users_online = parseInt(users_online);
  users_online++;
  $('.users-online').text(users_online.toString());
}

/*
* Removing a user from the list of users
* */
function removeUser(user) {
  // Remove the user from the list of users
  $('.users-container ul li').each(function(index) {
    if($(this).text() == user) {
      this.remove();
    }
  });

  // Decrement the users logged in counter
  var users_online = $('.users-online').text();
  users_online = parseInt(users_online);
  users_online--;
  $('.users-online').text(users_online.toString());
}

/*
* Welcome message that is shown when the chat is first fired up.
* The user doesn't have to be logged in to view this.
* */
function showWelcome() {
  addToChat('','Welcome to the chat!');
  cmdHelp('/help');
}


function initiateChat(username) {

    // Load in the online users, should include itself
    var online_users = getOnlineUsers();
    // foreach user, addToUsers(user)
        // code

    // Set input prompt to be the username
    console.log('Setting prompt to: '+ username);
    $('.input-prompt').text(username + ">");

    // Starting message listener
    manageMessageListener(true);
}

var interval = null;

function manageMessageListener(start) {
    if(start) {
        interval = setInterval(messageListener, 500);
    } else {
        clearInterval(interval);
    }
}

function messageListener() {
    console.log('Message Listener: Checking for new messages');
}

function ceaseChat() {
    // Reset the users prompt
    $('.input-prompt').text('>');

    // Remove all users from users list
    $('.users-container ul li').each(function(index) {
        this.remove();
    });

    // Set online user counter to 0
    $('.users-online').text('0');

    // Stop message listener
    manageMessageListener(false);
}

function getOnlineUsers() {
    $.post( "/wildfly-helloworld-mdb/getOnlineUsers")
        .done(function( data ) {
            console.log('Response from message post: ' + data);
        });
}