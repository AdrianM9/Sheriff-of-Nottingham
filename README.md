# Sheriff-of-Nottingham
The program simulates an entire game of "Sheriff of Nottingham" and shows the final results.

--- General logic:
	The program starts with the creation of the players and the initial deck of
assets. Then, the initializing of the game begins, which means that every
player takes 6 assets from the deck. Then, the 2 * numberOfPlayers rounds start.
Every round starts finding, first of all, who's the sheriff for the current
round and, then, for every player left (every merchant) the method for setting
the bag is called. Once all merchants loaded their bags, the sheriff starts
inspecting, choosing which bags to control. The confiscated assets are added at
the end of the deck.
	After the inspection stage, the remaining cards in the bag of every
merchant are moved on every's stand, every player completes the set of assets
in his hand and the next round starts playing.
	When all rounds have been played, the method for ending the game is called.
This method computes the score of every player and shows them ordered
descending by score.

--- assets package:
	The package contains an abstract class called "Asset". The class contains
the common fields for every asset and the methods which can be applied for
every asset.
	The LegalAsset and IllegalAsset classes extend the class Asset and have
specific fields and methods which can be applied on legal, respectively illegal
assets.
	Then there is a class for every particular asset, which extends the
corresponding class (LegalAsset/IllegalAsset). Every particular class cannot be
extended and constructs only one object of that type because these classes are
designed using the Singleton Pattern. This pattern is used here because there
is no action of modifying the object in the whole project, there are only
references to the object and I thought that creating more objects of the same
type is useless.
	The enumeration "AssetsIds" is used for storing the ids of the assets in an
elegant way.

--- factories package:
	This package contains a generic interface (IFactory) which allowed me to
implement two factories based on that interface. Both classes use the Singleton
Pattern, so there is only one factory for assets and only one factory for
players.
    The method of creating elements in AssetFactory class returns the only one
instance of the corresponding asset and the same method in PlayerFactory
creates and returns a player based on his strategy. Because the creation of the
players and of the assets is made only one time using a list, I've created two
methods for returning a list of assets and a list of players.

--- gameplay package:
	The Game class detains all the player in the game and the deck of assets.
There is a static inner class used for playing rounds called "Round". Every
call of start method in class Round will execute all actions which need to be
done in a round like moving assets in bag or on stand, inspecting the bags.
	When all rounds are played, the finish method in the Game class is
called. After finding the first and second biggest number of occurrences on
every stand of any legal asset type by calling the
computeNumberOfAssetsForKingQueenBonus method, the King's and Queen's Bonuses
are added to every player and then the final ranking is printed.

--- main package:
	The Main class is used for reading the input, then the class GameInput
stores the ids of the assets and the strategies of players in two lists, then
the "main" method in the Main class starts the game.

--- players package:
	The package contains an abstract class called "Player". The class contains
the common fields for every player and the methods which can be applied for
every player. When choosing which assets to load in bag, every player needs to
check the number of occurrences of the legal assets in his hand or the profit
for every illegal asset in his hand, that's why the methods like
computeOccurrencesOfLegalAssets or computeProfitsOfIllegalAssets are defined
in the Player class. Also, some players may use the basic strategy of choosing
assets to load in bag sometimes, so the basicAssetsToMove method is also
defined in the Player class.
	The Player class is extended by Basic, Bribed, Greedy and Wizard classes
which instantiate objects that act like players using the basic, bribe, greedy,
respectively wizard strategy.
