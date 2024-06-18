package ru.samsung.gamestudio.managers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import ru.samsung.gamestudio.GameSettings;
import ru.samsung.gamestudio.objects.GameObject;

public class ContactManager {

    World world;

    public ContactManager(World world) {
        this.world = world;

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                Fixture fixA = contact.getFixtureA();
                Fixture fixB = contact.getFixtureB();

                int cDef = fixA.getFilterData().categoryBits;
                int cDef2 = fixB.getFilterData().categoryBits;

                if (cDef == GameSettings.TRASH_BIT && cDef2 == GameSettings.BULLET_BIT
                        || cDef2 == GameSettings.TRASH_BIT && cDef == GameSettings.BULLET_BIT
                        || cDef == GameSettings.TRASH_BIT && cDef2 == GameSettings.SHIP_BIT
                        || cDef2 == GameSettings.TRASH_BIT && cDef == GameSettings.SHIP_BIT
                        || cDef == GameSettings.UFO_BIT && cDef2 == GameSettings.BULLET_BIT
                        || cDef2 == GameSettings.UFO_BIT && cDef == GameSettings.BULLET_BIT
                        || cDef == GameSettings.UFO_BIT && cDef2 == GameSettings.SHIP_BIT
                        || cDef2 == GameSettings.UFO_BIT && cDef == GameSettings.SHIP_BIT
                        || cDef == GameSettings.BOSS_BIT && cDef2 == GameSettings.BULLET_BIT
                        || cDef2 == GameSettings.BOSS_BIT && cDef == GameSettings.BULLET_BIT
                        || cDef == GameSettings.BOSS_BIT && cDef2 == GameSettings.SHIP_BIT
                        || cDef2 == GameSettings.BOSS_BIT && cDef == GameSettings.SHIP_BIT
                        || cDef == GameSettings.ENEMY_BULLET_BIT && cDef2 == GameSettings.BULLET_BIT
                        || cDef2 == GameSettings.ENEMY_BULLET_BIT && cDef == GameSettings.BULLET_BIT
                        || cDef == GameSettings.ENEMY_BULLET_BIT && cDef2 == GameSettings.SHIP_BIT
                        || cDef2 == GameSettings.ENEMY_BULLET_BIT && cDef == GameSettings.SHIP_BIT) {

                    ((GameObject) fixA.getUserData()).hit();
                    ((GameObject) fixB.getUserData()).hit();

                }
                if (cDef == GameSettings.METEOR_BIT && cDef2 == GameSettings.BULLET_BIT
                        || cDef2 == GameSettings.METEOR_BIT && cDef == GameSettings.BULLET_BIT) {
                    ((GameObject) fixA.getUserData()).body.setLinearVelocity(0, 0);
                    ((GameObject) fixB.getUserData()).body.setLinearVelocity(0, 0);
                    ((GameObject) fixA.getUserData()).body.setLinearVelocity(new Vector2(0, -GameSettings.METEOR_VELOCITY));
                    ((GameObject) fixB.getUserData()).body.setLinearVelocity(new Vector2(0, -GameSettings.METEOR_VELOCITY));
                    ((GameObject) fixA.getUserData()).hit();
                    ((GameObject) fixB.getUserData()).hit();
                }
                if (cDef == GameSettings.METEOR_BIT && cDef2 == GameSettings.SHIP_BIT
                        || cDef2 == GameSettings.METEOR_BIT && cDef == GameSettings.SHIP_BIT) {
                    ((GameObject) fixA.getUserData()).bump();
                    ((GameObject) fixB.getUserData()).bump();
                }
            }

            @Override
            public void endContact(Contact contact) {
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });

    }

}
