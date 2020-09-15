# Fake Texts
### An app that simulates texting

This app is a simple UI that looks like a messaging app, allows a user to send messages, and
simulates responses. New messages are shown at the bottom of the list, and the list is pushed up as
more messages are sent and received. Outgoing messages appear in pink on the right, while incoming 
messages are on the left in grey. If a message is sent or received more than one hour after the last 
message in the chat, a timestamp is shown above the new message with the day and time.

The app uses MVVM architecture with the ViewModel using LiveData to send updates to the UI layer. 
Dependency injection is handled by Dagger2 and data binding uses Kotlin synthetic views. The 
messages are displayed in a RecyclerView using 3 custom view holders; one for sent messages, one for 
received messages, and one for timestamps. There are unit tests for the ViewModel's logic around 
message tails and timestamps, and for the Calendar class extension.

## Message tails
Some messages have tails. A message has a tail when any of the following 3 criteria are met:
 * It is the most recent message in the conversation
 * The message after it is sent by the other user
 * The message after it was sent more than 20 seconds afterwards

## New message animations
New messages are added to the list with the default animation of Android's RecyclerView. One of the
original aims of the app was to have this animation look like the animation that iMessage uses,
however due to time constraints implementing this was not possible. I attempted to add custom
animations to the RecyclerView, but these introduced bugs in the RecyclerView's updates where
message tails werenm't being removed and sometimes a message would "stick", resulting in it staying
in one place as new messages were added or the view was scrolled. With more time, I would investigate
this bug further and add some level of custom animation to new messages - perhaps even like iMessage 
(though this would purely be to implement that type of animation and not to copy Apple for the sake 
of it).

## Persistent data storage
This app does not have persistent data storage. Messages are stored in a list in the ViewModel, so 
are lost if the ViewModel is destroyed, e.g. when the app is closed. Despite never having used it 
before I experimented with Realm as a method of persisting the message data, however I couldn't get 
it working within a reasonable amount of time.

## Future development
Some ideas to take this app even further include:
 * Have a delay on the incoming messages, to make them seem more realistic. This could be done with 
 a Handler, but care would have to be taken to avoid memory leaks.
 * Update the timestamp to be more dynamic and relative; show "Today" or "Tomorrow" for those 
 days, and add the date for timestamps that are over a week ago.
 * More/smarter responses; currently the app only replies to "hello" and "how's it going".
 * More "people" to talk to; the app would launch onto a list of "contacts", and selecting one takes 
 the user to the chat history with that contact. The user can go back to the main list to "talk" to 
 a different contact and won't lose any chat history. 