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
        case 'privacy':
            cmdPrivacy(input);
            break;
        case 'friends':
            cmdFriends(input);
            break;

    }
  // If it doesn't start with a '/', than it's a chat message
  } else {
    console.log('CHAT MESSAGE DETECTED');
    console.log('Sending POST to /postMessage');
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
    var privacy  = "- /privacy {friends,everyone,noone} - choose who can see that you're online<br/>"
                 + "&nbsp;&nbsp;/privacy friends - will make you only visible for friends<br/>"
                 + "&nbsp;&nbsp;/privacy everyone - will make you visible for everyone<br/>"
                 + "&nbsp;&nbsp;/privacy noone - will make you invisible for everyone<br/>";
    var friends  = "- /friends - will show your current list of friends<br />"
                 + "&nbsp;&nbsp;/friends {add,remove} elliot - will add/remove elliot<br/>";

  var response = "";

  if(!input[1]) {
    response = "Here are the available commands:<br/>"
      + help
      + login
      + logout
      + register
      + privacy
      + friends;
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
        case 'friends':
            response = friends;
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
        console.log("More: " + data['SUCCESS']);
          console.log("More: " + typeof data['SUCCESS']);

          if(data['SUCCESS'] == 'TRUE') {
              addToChat('','Login successfull',false);
              initiateChat(username);
          } else {
              addToChat('','Login failed, try again',false);
          }

      },"json");
}

function cmdLogout(input) {
    input = input.substring(1).split(' ');
    // Check that username was supplied
    var response = "/logout";
    addToChat('',response,false);
    var post_logout = postLogout(username);
}

function postLogout(username) {
    $.post( "/wildfly-helloworld-mdb/logout", JSON.stringify({"username": username}))
        .done(function( data ) {
            if(data['SUCCESS'] == 'TRUE') {
                addToChat('','Logout successfull',false);
                ceaseChat(username);
            } else {
                addToChat('','Logout failed, try again',false);
            }
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
  } else {
    // if not show the help for /login
    input = '/help register';
    cmdHelp(input);
  }
}

function postRegister(username, password) {
  $.post( "/wildfly-helloworld-mdb/register", JSON.stringify({ "username": username, "password": password }))
      .done(function( data ) {
          if(data['SUCCESS'] == 'TRUE') {
              addToChat('','Registration successfull! Try to login :)',false);
          } else {
              addToChat('','Registration failed! Try another username.',false);
          }

      });
}

function cmdFriends(input) {
    input = input.substring(1).split(' ');
    var options = ['add', 'remove'];
    if(input[1] && input[2]) {
        var response = "/friends " + input[1] + " " + input[2];
        addToChat('', response, false);
        if (input[1] == 'add') {
            var post_add_friend = postAddFriend(input[2]);
        } else if (input[1] == 'remove') {
            var post_remove_friend = postRemoveFriend(input[2]);
        }

    } else {
        // if not show the help for /friends
        var response = "/friends"
        addToChat('', response, false);
        var post_get_friends = postGetFriends();
    }
}

function postGetFriends() {
    $.post( "/wildfly-helloworld-mdb/getFriends", JSON.stringify({ "username": username }))
        .done(function( data ) {
            if(data['SUCCESS'] == 'TRUE') {
                addToChat('','Here is a list of your friends! :)',false);
            } else {
                addToChat('','Could not find your friends! Try again.',false);
            }
        });
}

function postAddFriend(friend) {
    $.post( "/wildfly-helloworld-mdb/addFriend", JSON.stringify({ "username": username, "friend": friend }))
        .done(function( data ) {
            if(data['SUCCESS'] == 'TRUE') {
                addToChat('','Added a friend! :)',false);
            } else {
                addToChat('','Could not add a friend! Try again.',false);
            }
        });
}

function postRemoveFriend(friend) {
    $.post( "/wildfly-helloworld-mdb/removeFriend", JSON.stringify({ "username": username, "friend": friend }))
        .done(function( data ) {
            if(data['SUCCESS'] == 'TRUE') {
                addToChat('','Removed a friend :(',false);
            } else {
                addToChat('','Could not remove a friend! Try again.',false);
            }

        });
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
    msgContainer = '<div class="chat-message-container server-message">\n';
    msgContainer    += '  <span class="chat-message">'+message+'</span>\n';
  }
  msgContainer    += '</div>\n';

  $('.chat-container').append(msgContainer);
  $('body').scrollTop(1E10);
  window.scrollTo(0,document.body.scrollHeight);
}

function postMessage(message) {
  $.post( "/wildfly-helloworld-mdb/postMessage", JSON.stringify({ "username": username, "message": message }))
      .done(function( data ) {
        console.log('Response from message post: ' + data);

          // TODO: Add the message to chat container

      }, "json");
}

/*
* Adding a user to the list of users
* */
function addToUsers(id, user) {
    // Check if the user is already added
    var exists = false;
    $('.users-container ul li').each(function(index) {
       if($(this).text() == user) {
           exists = true;
       }
    });

    if(!exists) {
        // Add the username to the list of users
        $('.users-container ul').append('<li data-id="'+id+'">'+user+'</li>');

        // Increment the counter of online users
        var users_online = $('.users-online').text();
        users_online = parseInt(users_online);
        users_online++;
        $('.users-online').text(users_online.toString());
    }

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
    var welcome = "<ul>Welcome to the chat!</ul>";
    addToChat('',welcome);
  cmdHelp('/help');
}

var username = null;
function initiateChat(uname) {

    username = uname;

    // Load in the online users, should include itself
    var online_users = getOnlineUsers(username);

    // Set input prompt to be the username
    console.log('Setting prompt to: '+ username);
    $('.input-prompt').text(username + ">");

    // Starting message listener
    manageMessageListener(true);
}

var interval = null;
function manageMessageListener(start) {
    if(start) {
        interval = setInterval(messageListener, 3000);
    } else {
        clearInterval(interval);
    }
}

function messageListener() {
    console.log('Message Listener: Checking for new messages');

    // Check for the last seen ID in the chat
    var id_last_seen = getLastSeenId();

    getOnlineUsers(username);

    $.post( "/wildfly-helloworld-mdb/getMessages", JSON.stringify({"idLastSeen": id_last_seen.toString()}))
        .done(function( data ) {
            console.log('Response from getMessages get: ' + data);
            // TODO: Add each new message to the chat container
            for(var k in data) {
                addToChat(data[k].split(':')[0], data[k].split(':')[1], k);
            }
        }, "json");
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

function getLastSeenId() {
    var id = 0;
    $('.chat-message-container').each(function(index) {
        if($(this).data('id')) {
            id = $(this).data('id');
        }
    });
    return id;
}

function getOnlineUsers(username) {
    $.post( "/wildfly-helloworld-mdb/getOnlineUsers", JSON.stringify({"username":username}))
        .done(function( data ) {
            for(var k in data) {
                addToUsers(k, data[k]);
            }
        }, "json");
}

function cmdPrivacy(input) {
    input = input.substring(1).split(' ');
    if(input[1]) {
        var response = "/privacy "+input[1];
        addToChat('',response,false);
        if($.inArray(input[1], ["everyone", "noone", "friends"]) > -1) {
            switch(input[1]) {
                case "everyone":
                    var post_privacy = postPrivacy(0);
                    break;
                case "noone":
                    var post_privacy = postPrivacy(2);
                    break;
                case "friends":
                    var post_privacy = postPrivacy(1);
                    break;
            }
        }
    } else {
        // if not show the help for /login
        input = '/help privacy';
        cmdHelp(input);
    }
}

function postPrivacy(input) {
    $.post( "/wildfly-helloworld-mdb/privacy", JSON.stringify({ "status":input.toString() }))
        .done(function( data ) {
            console.log('Response from privacy post: ' + data);

            if(data['SUCCESS'] == 'TRUE') {
                addToChat('','Privacy updated!',false);
            } else {
                addToChat('','Could not update privacy! Try again.',false);
            }

        });

}