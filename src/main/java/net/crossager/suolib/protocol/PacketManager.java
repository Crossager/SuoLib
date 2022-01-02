package net.crossager.suolib.protocol;

import net.crossager.suolib.SuoLib;
import net.crossager.suolib.util.NMSUtils;
import net.crossager.suolib.util.Utils;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.protocol.game.PacketPlayOutEntity.*;
import net.minecraft.network.protocol.game.PacketPlayInFlying.*;
import net.minecraft.network.protocol.handshake.PacketHandshakingInListener;
import net.minecraft.network.protocol.handshake.PacketHandshakingInSetProtocol;
import net.minecraft.network.protocol.login.*;
import net.minecraft.network.protocol.status.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public final class PacketManager {
    private final ProtocolManager pm;
    public final Play PLAY;
    public final Status STATUS;
    public final Login LOGIN;
    public final Handshaking HANDSHAKING;

    public final class Play {
        private Play(){}
        public final Out OUT = new Out();
        public final In IN = new In();
        public final class Out {
            private Out(){}
            private final ProtocolContainer<PacketListenerPlayOut> PROTOCOL = pm.PLAY.OUT;
            public final PacketType<PacketPlayOutSpawnEntity, PacketListenerPlayOut> SPAWN_ENTITY = new PacketType<>(PROTOCOL, 0);
            public final PacketType<PacketPlayOutSpawnEntityExperienceOrb, PacketListenerPlayOut> SPAWN_ENTITY_EXPERIENCE_ORB = new PacketType<>(PROTOCOL, 1);
            public final PacketType<PacketPlayOutSpawnEntityLiving, PacketListenerPlayOut> SPAWN_ENTITY_LIVING = new PacketType<>(PROTOCOL, 2);
            public final PacketType<PacketPlayOutSpawnEntityPainting, PacketListenerPlayOut> SPAWN_ENTITY_PAINTING = new PacketType<>(PROTOCOL, 3);
            public final PacketType<PacketPlayOutNamedEntitySpawn, PacketListenerPlayOut> NAMED_ENTITY_SPAWN = new PacketType<>(PROTOCOL, 4);
            public final PacketType<ClientboundAddVibrationSignalPacket, PacketListenerPlayOut> ADD_VIBRATION_SIGNAL = new PacketType<>(PROTOCOL, 5);
            public final PacketType<PacketPlayOutAnimation, PacketListenerPlayOut> ANIMATION = new PacketType<>(PROTOCOL, 6);
            public final PacketType<PacketPlayOutStatistic, PacketListenerPlayOut> STATISTIC = new PacketType<>(PROTOCOL, 7);
            public final PacketType<PacketPlayOutBlockBreak, PacketListenerPlayOut> BLOCK_BREAK = new PacketType<>(PROTOCOL, 8);
            public final PacketType<PacketPlayOutBlockBreakAnimation, PacketListenerPlayOut> BLOCK_BREAK_ANIMATION = new PacketType<>(PROTOCOL, 9);
            public final PacketType<PacketPlayOutTileEntityData, PacketListenerPlayOut> TILE_ENTITY_DATA = new PacketType<>(PROTOCOL, 10);
            public final PacketType<PacketPlayOutBlockAction, PacketListenerPlayOut> BLOCK_ACTION = new PacketType<>(PROTOCOL, 11);
            public final PacketType<PacketPlayOutBlockChange, PacketListenerPlayOut> BLOCK_CHANGE = new PacketType<>(PROTOCOL, 12);
            public final PacketType<PacketPlayOutBoss, PacketListenerPlayOut> BOSS = new PacketType<>(PROTOCOL, 13);
            public final PacketType<PacketPlayOutServerDifficulty, PacketListenerPlayOut> SERVER_DIFFICULTY = new PacketType<>(PROTOCOL, 14);
            public final PacketType<PacketPlayOutChat, PacketListenerPlayOut> CHAT = new PacketType<>(PROTOCOL, 15);
            public final PacketType<ClientboundClearTitlesPacket, PacketListenerPlayOut> CLEAR_TITLES = new PacketType<>(PROTOCOL, 16);
            public final PacketType<PacketPlayOutTabComplete, PacketListenerPlayOut> TAB_COMPLETE = new PacketType<>(PROTOCOL, 17);
            public final PacketType<PacketPlayOutCommands, PacketListenerPlayOut> COMMANDS = new PacketType<>(PROTOCOL, 18);
            public final PacketType<PacketPlayOutCloseWindow, PacketListenerPlayOut> CLOSE_WINDOW = new PacketType<>(PROTOCOL, 19);
            public final PacketType<PacketPlayOutWindowItems, PacketListenerPlayOut> WINDOW_ITEMS = new PacketType<>(PROTOCOL, 20);
            public final PacketType<PacketPlayOutWindowData, PacketListenerPlayOut> WINDOW_DATA = new PacketType<>(PROTOCOL, 21);
            public final PacketType<PacketPlayOutSetSlot, PacketListenerPlayOut> SET_SLOT = new PacketType<>(PROTOCOL, 22);
            public final PacketType<PacketPlayOutSetCooldown, PacketListenerPlayOut> SET_COOLDOWN = new PacketType<>(PROTOCOL, 23);
            public final PacketType<PacketPlayOutCustomPayload, PacketListenerPlayOut> CUSTOM_PAYLOAD = new PacketType<>(PROTOCOL, 24);
            public final PacketType<PacketPlayOutCustomSoundEffect, PacketListenerPlayOut> CUSTOM_SOUND_EFFECT = new PacketType<>(PROTOCOL, 25);
            public final PacketType<PacketPlayOutKickDisconnect, PacketListenerPlayOut> KICK_DISCONNECT = new PacketType<>(PROTOCOL, 26);
            public final PacketType<PacketPlayOutEntityStatus, PacketListenerPlayOut> ENTITY_STATUS = new PacketType<>(PROTOCOL, 27);
            public final PacketType<PacketPlayOutExplosion, PacketListenerPlayOut> EXPLOSION = new PacketType<>(PROTOCOL, 28);
            public final PacketType<PacketPlayOutUnloadChunk, PacketListenerPlayOut> UNLOAD_CHUNK = new PacketType<>(PROTOCOL, 29);
            public final PacketType<PacketPlayOutGameStateChange, PacketListenerPlayOut> GAME_STATE_CHANGE = new PacketType<>(PROTOCOL, 30);
            public final PacketType<PacketPlayOutOpenWindowHorse, PacketListenerPlayOut> OPEN_WINDOW_HORSE = new PacketType<>(PROTOCOL, 31);
            public final PacketType<ClientboundInitializeBorderPacket, PacketListenerPlayOut> INITIALIZE_BORDER = new PacketType<>(PROTOCOL, 32);
            public final PacketType<PacketPlayOutKeepAlive, PacketListenerPlayOut> KEEP_ALIVE = new PacketType<>(PROTOCOL, 33);
            public final PacketType<ClientboundLevelChunkWithLightPacket, PacketListenerPlayOut> LEVEL_CHUNK_WITH_LIGHT = new PacketType<>(PROTOCOL, 34);
            public final PacketType<PacketPlayOutWorldEvent, PacketListenerPlayOut> WORLD_EVENT = new PacketType<>(PROTOCOL, 35);
            public final PacketType<PacketPlayOutWorldParticles, PacketListenerPlayOut> WORLD_PARTICLES = new PacketType<>(PROTOCOL, 36);
            public final PacketType<PacketPlayOutLightUpdate, PacketListenerPlayOut> LIGHT_UPDATE = new PacketType<>(PROTOCOL, 37);
            public final PacketType<PacketPlayOutLogin, PacketListenerPlayOut> LOGIN = new PacketType<>(PROTOCOL, 38);
            public final PacketType<PacketPlayOutMap, PacketListenerPlayOut> MAP = new PacketType<>(PROTOCOL, 39);
            public final PacketType<PacketPlayOutOpenWindowMerchant, PacketListenerPlayOut> OPEN_WINDOW_MERCHANT = new PacketType<>(PROTOCOL, 40);
            public final PacketType<PacketPlayOutRelEntityMove, PacketListenerPlayOut> REL_ENTITY_MOVE = new PacketType<>(PROTOCOL, 41);
            public final PacketType<PacketPlayOutRelEntityMoveLook, PacketListenerPlayOut> REL_ENTITY_MOVE_LOOK = new PacketType<>(PROTOCOL, 42);
            public final PacketType<PacketPlayOutEntityLook, PacketListenerPlayOut> ENTITY_LOOK = new PacketType<>(PROTOCOL, 43);
            public final PacketType<PacketPlayOutVehicleMove, PacketListenerPlayOut> VEHICLE_MOVE = new PacketType<>(PROTOCOL, 44);
            public final PacketType<PacketPlayOutOpenBook, PacketListenerPlayOut> OPEN_BOOK = new PacketType<>(PROTOCOL, 45);
            public final PacketType<PacketPlayOutOpenWindow, PacketListenerPlayOut> OPEN_WINDOW = new PacketType<>(PROTOCOL, 46);
            public final PacketType<PacketPlayOutOpenSignEditor, PacketListenerPlayOut> OPEN_SIGN_EDITOR = new PacketType<>(PROTOCOL, 47);
            public final PacketType<ClientboundPingPacket, PacketListenerPlayOut> PING = new PacketType<>(PROTOCOL, 48);
            public final PacketType<PacketPlayOutAutoRecipe, PacketListenerPlayOut> AUTO_RECIPE = new PacketType<>(PROTOCOL, 49);
            public final PacketType<PacketPlayOutAbilities, PacketListenerPlayOut> ABILITIES = new PacketType<>(PROTOCOL, 50);
            public final PacketType<ClientboundPlayerCombatEndPacket, PacketListenerPlayOut> PLAYER_COMBAT_END = new PacketType<>(PROTOCOL, 51);
            public final PacketType<ClientboundPlayerCombatEnterPacket, PacketListenerPlayOut> PLAYER_COMBAT_ENTER = new PacketType<>(PROTOCOL, 52);
            public final PacketType<ClientboundPlayerCombatKillPacket, PacketListenerPlayOut> PLAYER_COMBAT_KILL = new PacketType<>(PROTOCOL, 53);
            public final PacketType<PacketPlayOutPlayerInfo, PacketListenerPlayOut> PLAYER_INFO = new PacketType<>(PROTOCOL, 54);
            public final PacketType<PacketPlayOutLookAt, PacketListenerPlayOut> LOOK_AT = new PacketType<>(PROTOCOL, 55);
            public final PacketType<PacketPlayOutPosition, PacketListenerPlayOut> POSITION = new PacketType<>(PROTOCOL, 56);
            public final PacketType<PacketPlayOutRecipes, PacketListenerPlayOut> RECIPES = new PacketType<>(PROTOCOL, 57);
            public final PacketType<PacketPlayOutEntityDestroy, PacketListenerPlayOut> ENTITY_DESTROY = new PacketType<>(PROTOCOL, 58);
            public final PacketType<PacketPlayOutRemoveEntityEffect, PacketListenerPlayOut> REMOVE_ENTITY_EFFECT = new PacketType<>(PROTOCOL, 59);
            public final PacketType<PacketPlayOutResourcePackSend, PacketListenerPlayOut> RESOURCE_PACK_SEND = new PacketType<>(PROTOCOL, 60);
            public final PacketType<PacketPlayOutRespawn, PacketListenerPlayOut> RESPAWN = new PacketType<>(PROTOCOL, 61);
            public final PacketType<PacketPlayOutEntityHeadRotation, PacketListenerPlayOut> ENTITY_HEAD_ROTATION = new PacketType<>(PROTOCOL, 62);
            public final PacketType<PacketPlayOutMultiBlockChange, PacketListenerPlayOut> MULTI_BLOCK_CHANGE = new PacketType<>(PROTOCOL, 63);
            public final PacketType<PacketPlayOutSelectAdvancementTab, PacketListenerPlayOut> SELECT_ADVANCEMENT_TAB = new PacketType<>(PROTOCOL, 64);
            public final PacketType<ClientboundSetActionBarTextPacket, PacketListenerPlayOut> SET_ACTION_BAR_TEXT = new PacketType<>(PROTOCOL, 65);
            public final PacketType<ClientboundSetBorderCenterPacket, PacketListenerPlayOut> SET_BORDER_CENTER = new PacketType<>(PROTOCOL, 66);
            public final PacketType<ClientboundSetBorderLerpSizePacket, PacketListenerPlayOut> SET_BORDER_LERP_SIZE = new PacketType<>(PROTOCOL, 67);
            public final PacketType<ClientboundSetBorderSizePacket, PacketListenerPlayOut> SET_BORDER_SIZE = new PacketType<>(PROTOCOL, 68);
            public final PacketType<ClientboundSetBorderWarningDelayPacket, PacketListenerPlayOut> SET_BORDER_WARNING_DELAY = new PacketType<>(PROTOCOL, 69);
            public final PacketType<ClientboundSetBorderWarningDistancePacket, PacketListenerPlayOut> SET_BORDER_WARNING_DISTANCE = new PacketType<>(PROTOCOL, 70);
            public final PacketType<PacketPlayOutCamera, PacketListenerPlayOut> CAMERA = new PacketType<>(PROTOCOL, 71);
            public final PacketType<PacketPlayOutHeldItemSlot, PacketListenerPlayOut> HELD_ITEM_SLOT = new PacketType<>(PROTOCOL, 72);
            public final PacketType<PacketPlayOutViewCentre, PacketListenerPlayOut> VIEW_CENTRE = new PacketType<>(PROTOCOL, 73);
            public final PacketType<PacketPlayOutViewDistance, PacketListenerPlayOut> VIEW_DISTANCE = new PacketType<>(PROTOCOL, 74);
            public final PacketType<PacketPlayOutSpawnPosition, PacketListenerPlayOut> SPAWN_POSITION = new PacketType<>(PROTOCOL, 75);
            public final PacketType<PacketPlayOutScoreboardDisplayObjective, PacketListenerPlayOut> SCOREBOARD_DISPLAY_OBJECTIVE = new PacketType<>(PROTOCOL, 76);
            public final PacketType<PacketPlayOutEntityMetadata, PacketListenerPlayOut> ENTITY_METADATA = new PacketType<>(PROTOCOL, 77);
            public final PacketType<PacketPlayOutAttachEntity, PacketListenerPlayOut> ATTACH_ENTITY = new PacketType<>(PROTOCOL, 78);
            public final PacketType<PacketPlayOutEntityVelocity, PacketListenerPlayOut> ENTITY_VELOCITY = new PacketType<>(PROTOCOL, 79);
            public final PacketType<PacketPlayOutEntityEquipment, PacketListenerPlayOut> ENTITY_EQUIPMENT = new PacketType<>(PROTOCOL, 80);
            public final PacketType<PacketPlayOutExperience, PacketListenerPlayOut> EXPERIENCE = new PacketType<>(PROTOCOL, 81);
            public final PacketType<PacketPlayOutUpdateHealth, PacketListenerPlayOut> UPDATE_HEALTH = new PacketType<>(PROTOCOL, 82);
            public final PacketType<PacketPlayOutScoreboardObjective, PacketListenerPlayOut> SCOREBOARD_OBJECTIVE = new PacketType<>(PROTOCOL, 83);
            public final PacketType<PacketPlayOutMount, PacketListenerPlayOut> MOUNT = new PacketType<>(PROTOCOL, 84);
            public final PacketType<PacketPlayOutScoreboardTeam, PacketListenerPlayOut> SCOREBOARD_TEAM = new PacketType<>(PROTOCOL, 85);
            public final PacketType<PacketPlayOutScoreboardScore, PacketListenerPlayOut> SCOREBOARD_SCORE = new PacketType<>(PROTOCOL, 86);
            public final PacketType<ClientboundSetSimulationDistancePacket, PacketListenerPlayOut> SET_SIMULATION_DISTANCE = new PacketType<>(PROTOCOL, 87);
            public final PacketType<ClientboundSetSubtitleTextPacket, PacketListenerPlayOut> SET_SUBTITLE_TEXT = new PacketType<>(PROTOCOL, 88);
            public final PacketType<PacketPlayOutUpdateTime, PacketListenerPlayOut> UPDATE_TIME = new PacketType<>(PROTOCOL, 89);
            public final PacketType<ClientboundSetTitleTextPacket, PacketListenerPlayOut> SET_TITLE_TEXT = new PacketType<>(PROTOCOL, 90);
            public final PacketType<ClientboundSetTitlesAnimationPacket, PacketListenerPlayOut> SET_TITLES_ANIMATION = new PacketType<>(PROTOCOL, 91);
            public final PacketType<PacketPlayOutEntitySound, PacketListenerPlayOut> ENTITY_SOUND = new PacketType<>(PROTOCOL, 92);
            public final PacketType<PacketPlayOutNamedSoundEffect, PacketListenerPlayOut> NAMED_SOUND_EFFECT = new PacketType<>(PROTOCOL, 93);
            public final PacketType<PacketPlayOutStopSound, PacketListenerPlayOut> STOP_SOUND = new PacketType<>(PROTOCOL, 94);
            public final PacketType<PacketPlayOutPlayerListHeaderFooter, PacketListenerPlayOut> PLAYER_LIST_HEADER_FOOTER = new PacketType<>(PROTOCOL, 95);
            public final PacketType<PacketPlayOutNBTQuery, PacketListenerPlayOut> NBT_QUERY = new PacketType<>(PROTOCOL, 96);
            public final PacketType<PacketPlayOutCollect, PacketListenerPlayOut> COLLECT = new PacketType<>(PROTOCOL, 97);
            public final PacketType<PacketPlayOutEntityTeleport, PacketListenerPlayOut> ENTITY_TELEPORT = new PacketType<>(PROTOCOL, 98);
            public final PacketType<PacketPlayOutAdvancements, PacketListenerPlayOut> ADVANCEMENTS = new PacketType<>(PROTOCOL, 99);
            public final PacketType<PacketPlayOutUpdateAttributes, PacketListenerPlayOut> UPDATE_ATTRIBUTES = new PacketType<>(PROTOCOL, 100);
            public final PacketType<PacketPlayOutEntityEffect, PacketListenerPlayOut> ENTITY_EFFECT = new PacketType<>(PROTOCOL, 101);
            public final PacketType<PacketPlayOutRecipeUpdate, PacketListenerPlayOut> RECIPE_UPDATE = new PacketType<>(PROTOCOL, 102);
            public final PacketType<PacketPlayOutTags, PacketListenerPlayOut> TAGS = new PacketType<>(PROTOCOL, 103);

            public List<PacketType<? extends Packet<? extends PacketListenerPlayOut>, PacketListenerPlayOut>> getPackets() {
                List<PacketType<? extends Packet<? extends PacketListenerPlayOut>, PacketListenerPlayOut>> all = new ArrayList<>();
                all.addAll(List.of(SPAWN_ENTITY, SPAWN_ENTITY_EXPERIENCE_ORB, SPAWN_ENTITY_LIVING, SPAWN_ENTITY_PAINTING, NAMED_ENTITY_SPAWN, ADD_VIBRATION_SIGNAL, ANIMATION,
                        STATISTIC, BLOCK_BREAK, BLOCK_BREAK_ANIMATION, TILE_ENTITY_DATA, BLOCK_ACTION, BLOCK_CHANGE,
                        BOSS, SERVER_DIFFICULTY, CHAT, CLEAR_TITLES, TAB_COMPLETE, COMMANDS,
                        CLOSE_WINDOW, WINDOW_ITEMS, WINDOW_DATA, SET_SLOT, SET_COOLDOWN, CUSTOM_PAYLOAD,
                        CUSTOM_SOUND_EFFECT, KICK_DISCONNECT, ENTITY_STATUS, EXPLOSION, UNLOAD_CHUNK, GAME_STATE_CHANGE,
                        OPEN_WINDOW_HORSE, INITIALIZE_BORDER, KEEP_ALIVE, LEVEL_CHUNK_WITH_LIGHT, WORLD_EVENT, WORLD_PARTICLES,
                        LIGHT_UPDATE, LOGIN, MAP, OPEN_WINDOW_MERCHANT, REL_ENTITY_MOVE, REL_ENTITY_MOVE_LOOK,
                        ENTITY_LOOK, VEHICLE_MOVE, OPEN_BOOK, OPEN_WINDOW, OPEN_SIGN_EDITOR, PING,
                        AUTO_RECIPE, ABILITIES, PLAYER_COMBAT_END, PLAYER_COMBAT_ENTER, PLAYER_COMBAT_KILL, PLAYER_INFO,
                        LOOK_AT, POSITION, RECIPES, ENTITY_DESTROY, REMOVE_ENTITY_EFFECT, RESOURCE_PACK_SEND,
                        RESPAWN, ENTITY_HEAD_ROTATION, MULTI_BLOCK_CHANGE, SELECT_ADVANCEMENT_TAB, SET_ACTION_BAR_TEXT, SET_BORDER_CENTER,
                        SET_BORDER_LERP_SIZE, SET_BORDER_SIZE, SET_BORDER_WARNING_DELAY, SET_BORDER_WARNING_DISTANCE, CAMERA, HELD_ITEM_SLOT,
                        VIEW_CENTRE, VIEW_DISTANCE, SPAWN_POSITION, SCOREBOARD_DISPLAY_OBJECTIVE, ENTITY_METADATA, ATTACH_ENTITY,
                        ENTITY_VELOCITY, ENTITY_EQUIPMENT, EXPERIENCE, UPDATE_HEALTH, SCOREBOARD_OBJECTIVE, MOUNT,
                        SCOREBOARD_TEAM, SCOREBOARD_SCORE, SET_SIMULATION_DISTANCE, SET_SUBTITLE_TEXT, UPDATE_TIME, SET_TITLE_TEXT,
                        SET_TITLES_ANIMATION, ENTITY_SOUND, NAMED_SOUND_EFFECT, STOP_SOUND, PLAYER_LIST_HEADER_FOOTER, NBT_QUERY,
                        COLLECT, ENTITY_TELEPORT, ADVANCEMENTS, UPDATE_ATTRIBUTES, ENTITY_EFFECT, RECIPE_UPDATE,
                        TAGS));
                all.addAll(PROTOCOL.customPackets);
                return all;
            }
        }
        public final class In{
            private In(){}
            private final ProtocolContainer<PacketListenerPlayIn> PROTOCOL = pm.PLAY.IN;
            public final PacketType<PacketPlayInTeleportAccept, PacketListenerPlayIn> TELEPORT_ACCEPT = new PacketType<>(PROTOCOL, 0);
            public final PacketType<PacketPlayInTileNBTQuery, PacketListenerPlayIn> TILE_NBT_QUERY = new PacketType<>(PROTOCOL, 1);
            public final PacketType<PacketPlayInDifficultyChange, PacketListenerPlayIn> DIFFICULTY_CHANGE = new PacketType<>(PROTOCOL, 2);
            public final PacketType<PacketPlayInChat, PacketListenerPlayIn> CHAT = new PacketType<>(PROTOCOL, 3);
            public final PacketType<PacketPlayInClientCommand, PacketListenerPlayIn> CLIENT_COMMAND = new PacketType<>(PROTOCOL, 4);
            public final PacketType<PacketPlayInSettings, PacketListenerPlayIn> SETTINGS = new PacketType<>(PROTOCOL, 5);
            public final PacketType<PacketPlayInTabComplete, PacketListenerPlayIn> TAB_COMPLETE = new PacketType<>(PROTOCOL, 6);
            public final PacketType<PacketPlayInEnchantItem, PacketListenerPlayIn> ENCHANT_ITEM = new PacketType<>(PROTOCOL, 7);
            public final PacketType<PacketPlayInWindowClick, PacketListenerPlayIn> WINDOW_CLICK = new PacketType<>(PROTOCOL, 8);
            public final PacketType<PacketPlayInCloseWindow, PacketListenerPlayIn> CLOSE_WINDOW = new PacketType<>(PROTOCOL, 9);
            public final PacketType<PacketPlayInCustomPayload, PacketListenerPlayIn> CUSTOM_PAYLOAD = new PacketType<>(PROTOCOL, 10);
            public final PacketType<PacketPlayInBEdit, PacketListenerPlayIn> B_EDIT = new PacketType<>(PROTOCOL, 11);
            public final PacketType<PacketPlayInEntityNBTQuery, PacketListenerPlayIn> ENTITY_NBT_QUERY = new PacketType<>(PROTOCOL, 12);
            public final PacketType<PacketPlayInUseEntity, PacketListenerPlayIn> USE_ENTITY = new PacketType<>(PROTOCOL, 13);
            public final PacketType<PacketPlayInJigsawGenerate, PacketListenerPlayIn> JIGSAW_GENERATE = new PacketType<>(PROTOCOL, 14);
            public final PacketType<PacketPlayInKeepAlive, PacketListenerPlayIn> KEEP_ALIVE = new PacketType<>(PROTOCOL, 15);
            public final PacketType<PacketPlayInDifficultyLock, PacketListenerPlayIn> DIFFICULTY_LOCK = new PacketType<>(PROTOCOL, 16);
            public final PacketType<PacketPlayInPosition, PacketListenerPlayIn> POSITION = new PacketType<>(PROTOCOL, 17);
            public final PacketType<PacketPlayInPositionLook, PacketListenerPlayIn> POSITION_LOOK = new PacketType<>(PROTOCOL, 18);
            public final PacketType<PacketPlayInLook, PacketListenerPlayIn> LOOK = new PacketType<>(PROTOCOL, 19);
            public final PacketType<d, PacketListenerPlayIn> D = new PacketType<>(PROTOCOL, 20);
            public final PacketType<PacketPlayInVehicleMove, PacketListenerPlayIn> VEHICLE_MOVE = new PacketType<>(PROTOCOL, 21);
            public final PacketType<PacketPlayInBoatMove, PacketListenerPlayIn> BOAT_MOVE = new PacketType<>(PROTOCOL, 22);
            public final PacketType<PacketPlayInPickItem, PacketListenerPlayIn> PICK_ITEM = new PacketType<>(PROTOCOL, 23);
            public final PacketType<PacketPlayInAutoRecipe, PacketListenerPlayIn> AUTO_RECIPE = new PacketType<>(PROTOCOL, 24);
            public final PacketType<PacketPlayInAbilities, PacketListenerPlayIn> ABILITIES = new PacketType<>(PROTOCOL, 25);
            public final PacketType<PacketPlayInBlockDig, PacketListenerPlayIn> BLOCK_DIG = new PacketType<>(PROTOCOL, 26);
            public final PacketType<PacketPlayInEntityAction, PacketListenerPlayIn> ENTITY_ACTION = new PacketType<>(PROTOCOL, 27);
            public final PacketType<PacketPlayInSteerVehicle, PacketListenerPlayIn> STEER_VEHICLE = new PacketType<>(PROTOCOL, 28);
            public final PacketType<ServerboundPongPacket, PacketListenerPlayIn> PONG = new PacketType<>(PROTOCOL, 29);
            public final PacketType<PacketPlayInRecipeSettings, PacketListenerPlayIn> RECIPE_SETTINGS = new PacketType<>(PROTOCOL, 30);
            public final PacketType<PacketPlayInRecipeDisplayed, PacketListenerPlayIn> RECIPE_DISPLAYED = new PacketType<>(PROTOCOL, 31);
            public final PacketType<PacketPlayInItemName, PacketListenerPlayIn> ITEM_NAME = new PacketType<>(PROTOCOL, 32);
            public final PacketType<PacketPlayInResourcePackStatus, PacketListenerPlayIn> RESOURCE_PACK_STATUS = new PacketType<>(PROTOCOL, 33);
            public final PacketType<PacketPlayInAdvancements, PacketListenerPlayIn> ADVANCEMENTS = new PacketType<>(PROTOCOL, 34);
            public final PacketType<PacketPlayInTrSel, PacketListenerPlayIn> TR_SEL = new PacketType<>(PROTOCOL, 35);
            public final PacketType<PacketPlayInBeacon, PacketListenerPlayIn> BEACON = new PacketType<>(PROTOCOL, 36);
            public final PacketType<PacketPlayInHeldItemSlot, PacketListenerPlayIn> HELD_ITEM_SLOT = new PacketType<>(PROTOCOL, 37);
            public final PacketType<PacketPlayInSetCommandBlock, PacketListenerPlayIn> SET_COMMAND_BLOCK = new PacketType<>(PROTOCOL, 38);
            public final PacketType<PacketPlayInSetCommandMinecart, PacketListenerPlayIn> SET_COMMAND_MINECART = new PacketType<>(PROTOCOL, 39);
            public final PacketType<PacketPlayInSetCreativeSlot, PacketListenerPlayIn> SET_CREATIVE_SLOT = new PacketType<>(PROTOCOL, 40);
            public final PacketType<PacketPlayInSetJigsaw, PacketListenerPlayIn> SET_JIGSAW = new PacketType<>(PROTOCOL, 41);
            public final PacketType<PacketPlayInStruct, PacketListenerPlayIn> STRUCT = new PacketType<>(PROTOCOL, 42);
            public final PacketType<PacketPlayInUpdateSign, PacketListenerPlayIn> UPDATE_SIGN = new PacketType<>(PROTOCOL, 43);
            public final PacketType<PacketPlayInArmAnimation, PacketListenerPlayIn> ARM_ANIMATION = new PacketType<>(PROTOCOL, 44);
            public final PacketType<PacketPlayInSpectate, PacketListenerPlayIn> SPECTATE = new PacketType<>(PROTOCOL, 45);
            public final PacketType<PacketPlayInUseItem, PacketListenerPlayIn> USE_ITEM = new PacketType<>(PROTOCOL, 46);
            public final PacketType<PacketPlayInBlockPlace, PacketListenerPlayIn> BLOCK_PLACE = new PacketType<>(PROTOCOL, 47);

            public List<PacketType<? extends Packet<? extends PacketListenerPlayIn>, PacketListenerPlayIn>> getPackets() {
                List<PacketType<? extends Packet<? extends PacketListenerPlayIn>, PacketListenerPlayIn>> all = new ArrayList<>();
                all.addAll(List.of(TELEPORT_ACCEPT, TILE_NBT_QUERY, DIFFICULTY_CHANGE, CHAT, CLIENT_COMMAND, SETTINGS, TAB_COMPLETE,
                        ENCHANT_ITEM, WINDOW_CLICK, CLOSE_WINDOW, CUSTOM_PAYLOAD, B_EDIT, ENTITY_NBT_QUERY,
                        USE_ENTITY, JIGSAW_GENERATE, KEEP_ALIVE, DIFFICULTY_LOCK, POSITION, POSITION_LOOK,
                        LOOK, D, VEHICLE_MOVE, BOAT_MOVE, PICK_ITEM, AUTO_RECIPE,
                        ABILITIES, BLOCK_DIG, ENTITY_ACTION, STEER_VEHICLE, PONG, RECIPE_SETTINGS,
                        RECIPE_DISPLAYED, ITEM_NAME, RESOURCE_PACK_STATUS, ADVANCEMENTS, TR_SEL, BEACON,
                        HELD_ITEM_SLOT, SET_COMMAND_BLOCK, SET_COMMAND_MINECART, SET_CREATIVE_SLOT, SET_JIGSAW, STRUCT,
                        UPDATE_SIGN, ARM_ANIMATION, SPECTATE, USE_ITEM, BLOCK_PLACE));
                all.addAll(PROTOCOL.customPackets);
                return all;
            }
        }
    }
    public final class Status {
        private Status(){}
        public final Out OUT = new Out();
        public final In IN = new In();
        public final class Out{
            private Out(){}
            private final ProtocolContainer<PacketStatusOutListener> PROTOCOL = pm.STATUS.OUT;
            public final PacketType<PacketStatusOutServerInfo, PacketStatusOutListener> SERVER_INFO = new PacketType<>(PROTOCOL, 0);
            public final PacketType<PacketStatusOutPong, PacketStatusOutListener> PONG = new PacketType<>(PROTOCOL, 1);

            public List<PacketType<? extends Packet<? extends PacketStatusOutListener>, PacketStatusOutListener>> getPackets() {
                List<PacketType<? extends Packet<? extends PacketStatusOutListener>, PacketStatusOutListener>> all = new ArrayList<>();
                all.addAll(List.of(PONG));
                all.addAll(PROTOCOL.customPackets);
                return all;
            }
        }
        public final class In{
            private In(){}
            private final ProtocolContainer<PacketStatusInListener> PROTOCOL = pm.STATUS.IN;
            public final PacketType<PacketStatusInStart, PacketStatusInListener> START = new PacketType<>(PROTOCOL, 0);
            public final PacketType<PacketStatusInPing, PacketStatusInListener> PING = new PacketType<>(PROTOCOL, 1);

            public List<PacketType<? extends Packet<? extends PacketStatusInListener>, PacketStatusInListener>> getPackets() {
                List<PacketType<? extends Packet<? extends PacketStatusInListener>, PacketStatusInListener>> all = new ArrayList<>();
                all.addAll(List.of(START, PING));
                all.addAll(PROTOCOL.customPackets);
                return all;
            }
        }
    }
    public final class Login {
        private Login(){}
        public final Out OUT = new Out();
        public final In IN = new In();
        public final class Out{
            private Out(){}
            private final ProtocolContainer<PacketLoginOutListener> PROTOCOL = pm.LOGIN.OUT;
            public final PacketType<PacketLoginOutDisconnect, PacketLoginOutListener> DISCONNECT = new PacketType<>(PROTOCOL, 0);
            public final PacketType<PacketLoginOutEncryptionBegin, PacketLoginOutListener> ENCRYPTION_BEGIN = new PacketType<>(PROTOCOL, 1);
            public final PacketType<PacketLoginOutSuccess, PacketLoginOutListener> SUCCESS = new PacketType<>(PROTOCOL, 2);
            public final PacketType<PacketLoginOutSetCompression, PacketLoginOutListener> SET_COMPRESSION = new PacketType<>(PROTOCOL, 3);
            public final PacketType<PacketLoginOutCustomPayload, PacketLoginOutListener> CUSTOM_PAYLOAD = new PacketType<>(PROTOCOL, 4);

            public List<PacketType<? extends Packet<? extends PacketLoginOutListener>, PacketLoginOutListener>> getPackets() {
                List<PacketType<? extends Packet<? extends PacketLoginOutListener>, PacketLoginOutListener>> all = new ArrayList<>();
                all.addAll(List.of(DISCONNECT, ENCRYPTION_BEGIN, SUCCESS, SET_COMPRESSION, CUSTOM_PAYLOAD));
                all.addAll(PROTOCOL.customPackets);
                return all;
            }
        }
        public final class In {
            private In(){}
            private final ProtocolContainer<PacketLoginInListener> PROTOCOL = pm.LOGIN.IN;
            public final PacketType<PacketLoginInStart, PacketLoginInListener> START = new PacketType<>(PROTOCOL, 0);
            public final PacketType<PacketLoginInEncryptionBegin, PacketLoginInListener> ENCRYPTION_BEGIN = new PacketType<>(PROTOCOL, 1);
            public final PacketType<PacketLoginInCustomPayload, PacketLoginInListener> CUSTOM_PAYLOAD = new PacketType<>(PROTOCOL, 2);

            public List<PacketType<? extends Packet<? extends PacketLoginInListener>, PacketLoginInListener>> getPackets() {
                List<PacketType<? extends Packet<? extends PacketLoginInListener>, PacketLoginInListener>> all = new ArrayList<>();
                all.addAll(List.of(START, ENCRYPTION_BEGIN, CUSTOM_PAYLOAD));
                all.addAll(PROTOCOL.customPackets);
                return all;
            }
        }
    }
    public class Handshaking {
        private Handshaking(){}
        public final In IN = new In();
        public final class In{
            private In(){}
            private final ProtocolContainer<PacketHandshakingInListener> PROTOCOL = pm.HANDSHAKING.IN;
            public final PacketType<PacketHandshakingInSetProtocol, PacketHandshakingInListener> SET_PROTOCOL = new PacketType<>(PROTOCOL, 0);

            public List<PacketType<? extends Packet<? extends PacketHandshakingInListener>, PacketHandshakingInListener>> getPackets() {
                List<PacketType<? extends Packet<? extends PacketHandshakingInListener>, PacketHandshakingInListener>> all = new ArrayList<>();
                all.addAll(List.of(SET_PROTOCOL));
                all.addAll(PROTOCOL.customPackets);
                return all;
            }
        }
    }

    public PacketManager(ProtocolManager pm)
    {
        this.pm = pm;
        PLAY = new Play();
        STATUS = new Status();
        LOGIN = new Login();
        HANDSHAKING = new Handshaking();
    }

    public List<PacketType<? extends Packet<? extends PacketListener>, ? extends PacketListener>> getPackets(){
        List<PacketType<? extends Packet<? extends PacketListener>, ? extends PacketListener>> list = new ArrayList<>();
        list.addAll(PLAY.OUT.getPackets());
        list.addAll(PLAY.IN.getPackets());
        list.addAll(STATUS.OUT.getPackets());
        list.addAll(STATUS.IN.getPackets());
        list.addAll(LOGIN.OUT.getPackets());
        list.addAll(LOGIN.IN.getPackets());
        list.addAll(HANDSHAKING.IN.getPackets());
        return list;
    }
    public List<net.crossager.suolib.protocol.PacketListener<? extends Packet<? extends PacketListener>>> getListeners(){
        List<net.crossager.suolib.protocol.PacketListener<? extends Packet<? extends PacketListener>>> list = new ArrayList<>();
        for(PacketType<? , ?> type : getPackets()){
            list.addAll(type.getListeners());
        }
        return list;
    }

    public static void sendPacket(Player p, Packet<? extends PacketListener> packet){
        NMSUtils.getHandle(p).b.a(packet);
    }
    /**
     * Down here was used to generate the code for what's above
     */
    public static void printPackets(){
        print(SuoLib.getProvider().getProtocolManager().PLAY.OUT, "PacketListenerPlayOut", "PLAY.OUT");
        print(SuoLib.getProvider().getProtocolManager().PLAY.IN, "PacketListenerPlayIn", "PLAY.IN");
        print(SuoLib.getProvider().getProtocolManager().STATUS.OUT, "PacketStatusOutListener", "STATUS.OUT");
        print(SuoLib.getProvider().getProtocolManager().STATUS.IN, "PacketStatusInListener", "STATUS.IN");
        print(SuoLib.getProvider().getProtocolManager().LOGIN.OUT, "PacketLoginOutListener", "LOGIN.OUT");
        print(SuoLib.getProvider().getProtocolManager().LOGIN.IN, "PacketLoginInListener", "LOGIN.IN");
        print(SuoLib.getProvider().getProtocolManager().HANDSHAKING.IN, "PacketHandshakingInListener", "HANDSHAKING.IN");
    }
    private static void print(ProtocolContainer pm, String s, String s1){
        Utils.log(s);
        Utils.log("private final ProtocolContainer<" + s + "> PROTOCOL = SuoLib.getProvider().getProtocolManager()." + s1 + ";");
        List<String> names = new ArrayList<>();
        for (int i = 0; i < pm.getPackets().size(); i++) {
            Utils.log("public final PacketType<" + pm.getPacket(i).getSimpleName() + ", " + s + "> " + getCleanName(pm.getPacket(i).getSimpleName()) + " = new PacketType(PROTOCOL, " +
                    i + ");");
            names.add(getCleanName(pm.getPacket(i).getSimpleName()));
        }
        int count = 0;
        Utils.log("public List<PacketType<? , " + s + ">> getPackets() {");
        Utils.log("return PacketManager.<" + s + ">asList(PROTOCOL, ");
        String s2 = "";
        for(String s3 : names){
            s2 = ( s2 + s3 + ", ");
            if(count == 6){
                Utils.log(s2);
                s2 = "";
                count = 0;
            }
            count++;
        }
        if(s2 != null && !s2.isEmpty())
            s2 = s2.substring(0, s2.length() - 2);
        Utils.log(s2 + ");}");
    }
    private static String getCleanName(String s){
        String newS = s.replaceAll("PacketPlayIn", "");
        newS = newS.replaceAll("PacketPlayOut", "");
        newS = newS.replaceAll("PacketStatusIn", "");
        newS = newS.replaceAll("PacketStatusOut", "");
        newS = newS.replaceAll("PacketPlayIn", "");
        newS = newS.replaceAll("PacketLoginOut", "");
        newS = newS.replaceAll("PacketLoginIn", "");
        newS = newS.replaceAll("PacketHandshakingIn", "");
        StringBuilder builder = new StringBuilder();
        builder.append(newS.charAt(0));
        for(int i = 1; i < newS.length(); i++){
            if(Character.isUpperCase(newS.charAt(i))){
                builder.append('_');
            }
            builder.append(newS.charAt(i));
        }
        char[] c = new char[builder.length()];
        for(int i = 0; i < builder.length(); i++){
            c[i] = builder.charAt(i);
        }
        newS = builder.toString();
        newS = newS.toUpperCase();
        newS = newS.replaceAll("CLIENTBOUND_", "");
        newS = newS.replaceAll("SERVERBOUND_", "");
        newS = newS.replaceAll("_PACKET", "");
        newS = newS.replaceAll("N_B_T", "NBT");
        return newS;
    }
}
