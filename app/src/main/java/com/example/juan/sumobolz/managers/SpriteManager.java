package com.example.juan.sumobolz.managers;

import com.example.juan.sumobolz.entities.Sprite;

import java.util.List;

import java.util.*;

/**
 * SpriteManager Class
 * <p>
 * <p>
 * The SpriteManager is responsible for holding all sprite objects that will be
 * treated throughout the game.
 * <p>
 * It's also in charge of removing the sprite objects that the GameEngine tells
 * it to remove.
 * <p>
 * All collections are used by the JavaFX application thread.
 * <p>
 * During each game loop, the sprite managers gives the GameWorld access to all sprites.
 * <p>
 * This helps avoiding the creation of lists lists to later be garbage collected.
 * It also avoid exponential iterations when detecting collisions.
 * <p>
 * It provide some performance gain.
 *
 * @author Juan
 * @see com.example.juan.sumobolz.GameEngine
 */
public class SpriteManager {
    /**
     * All the sprite objects currently in the GameWorld.
     */
    private final List<Sprite> GAME_ACTORS = new ArrayList<>();

    /**
     * A global single list used to check collision against other
     * sprite objects. It's a copy of the GAME_ACTORS list.
     */
    private final List<Sprite> CHECK_COLLISION_LIST = new ArrayList<>();

    /**
     * A global set used to hold the sprite objects to be removed.
     */
    private final Set<Sprite> SPRITES_TO_CLEAN = new HashSet<>();

    /**
     * Adds sprite objects to be added into the game.
     *
     * @param sprites - Sprites to be added.
     */
    public void addSprites(Sprite... sprites) {
        GAME_ACTORS.addAll(Arrays.asList(sprites));
    }

    /**
     * Clears the list of sprite objects in the collision check collection
     * (CHECK_COLLISION_LIST).
     */
    public void resetCollisionsToCheck() {
        CHECK_COLLISION_LIST.clear();
        CHECK_COLLISION_LIST.addAll(GAME_ACTORS);
    }

    /**
     * Adds sprite objects to be removed
     *
     * @param sprites varargs of sprite objects.
     */
    public void addSpritesToBeRemoved(Sprite... sprites) {
        if (sprites.length > 1) {
            SPRITES_TO_CLEAN.addAll(Arrays.asList((Sprite[]) sprites));
        } else {
            SPRITES_TO_CLEAN.add(sprites[0]);
        }
    }

    /**
     * Removes sprites from the game actors in the game.
     *
     * @param sprites - Sprites to be added.
     */
    public void removeSprites(Sprite... sprites) {
        GAME_ACTORS.removeAll(Arrays.asList(sprites));
    }

    /**
     * Removes sprite objects from all collections.
     * <p>
     * <p>
     * The sprite to be removed will also be removed from the
     * list of all sprites called 'GAME_ACTORS'.
     */
    public void cleanupSprites() {

        // remove from actors list
        GAME_ACTORS.removeAll(SPRITES_TO_CLEAN);

        // reset the clean up sprites
        SPRITES_TO_CLEAN.clear();
    }

    /**
     * Returns a list of sprite objects that assist in collision checks.
     * This is a copy of all current sprite objects (copy of GAME_ACTORS).
     *
     * @return CHECK_COLLISION_LIST
     */
    public List<Sprite> getCollisionsToCheck() {
        return CHECK_COLLISION_LIST;
    }

    /**
     * Returns all the sprites.
     *
     * @return List - All sprites in the game
     */
    public List<Sprite> getAllSprites() {
        return GAME_ACTORS;
    }

    /**
     * Returns a set of sprite objects to be removed from the GAME_ACTORS.
     *
     * @return SPRITES_TO_CLEAN
     */
    public Set getSpritesToBeRemoved() {
        return SPRITES_TO_CLEAN;
    }

    public void purgeAllSprites() {
        GAME_ACTORS.clear();
        CHECK_COLLISION_LIST.clear();
        SPRITES_TO_CLEAN.clear();
    }


}