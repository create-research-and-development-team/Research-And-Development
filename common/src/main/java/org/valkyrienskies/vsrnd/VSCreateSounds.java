package org.valkyrienskies.vsrnd;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.valkyrienskies.vsrnd.platform.api.DeferredRegister;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class VSCreateSounds {
	public static final Map<ResourceLocation, SoundEntry> ALL = new HashMap<>();
	public static final SoundEntry
			SUPERSONIC = create("supersonic").subtitle("Supersonic")
											 .category(SoundSource.RECORDS)
											 .attenuationDistance(32)
											 .build();
	private static final DeferredRegister<SoundEvent> sounds = DeferredRegister.create(Registry.SOUND_EVENT,
																					   VSCreateMod.MOD_ID);

	private static SoundEntryBuilder create(String name) {
		return create(VSCreateMod.asResource(name));
	}

	public static SoundEntryBuilder create(ResourceLocation id) {
		return new SoundEntryBuilder(id);
	}

	public static void register() {
        for (SoundEntry entry : ALL.values()) {
            entry.register();
        }

		sounds.registerAll();
	}

	public static JsonObject provideLangEntries() {
		JsonObject object = new JsonObject();
        for (SoundEntry entry : ALL.values()) {
            if (entry.hasSubtitle()) {
                object.addProperty(entry.getSubtitleKey(), entry.getSubtitle());
            }
        }
		return object;
	}

	public static SoundEntryProvider provider(DataGenerator generator) {
		return new SoundEntryProvider(generator);
	}


	private static class SoundEntryProvider implements DataProvider {

		private final DataGenerator generator;

		public SoundEntryProvider(DataGenerator generator) {
			this.generator = generator;
		}

		@Override
		public void run(HashCache cache) throws IOException {
			generate(generator.getOutputFolder(), cache);
		}

		@Override
		public String getName() {
			return "VS Create Template's Custom Sounds";
		}

		public void generate(Path path, HashCache cache) {
			Gson GSON = (new GsonBuilder()).setPrettyPrinting()
										   .disableHtmlEscaping()
										   .create();
			path = path.resolve("assets/vsrnd");

			try {
				JsonObject json = new JsonObject();
				ALL.entrySet()
				   .stream()
				   .sorted(Map.Entry.comparingByKey())
				   .forEach(entry -> {
					   entry.getValue()
							.write(json);
				   });
				DataProvider.save(GSON, cache, json, path.resolve("sounds.json"));

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public record ConfiguredSoundEvent(Supplier<SoundEvent> event, float volume, float pitch) {
	}

	public static class SoundEntryBuilder {

		protected ResourceLocation id;
		protected String subtitle = "unregistered";
		protected SoundSource category = SoundSource.BLOCKS;
		protected List<ConfiguredSoundEvent> wrappedEvents;
		protected List<ResourceLocation> variants;
		protected int attenuationDistance;

		public SoundEntryBuilder(ResourceLocation id) {
			wrappedEvents = new ArrayList<>();
			variants = new ArrayList<>();
			this.id = id;
		}

		public SoundEntryBuilder subtitle(String subtitle) {
			this.subtitle = subtitle;
			return this;
		}

		public SoundEntryBuilder attenuationDistance(int distance) {
			this.attenuationDistance = distance;
			return this;
		}

		public SoundEntryBuilder noSubtitle() {
			this.subtitle = null;
			return this;
		}

		public SoundEntryBuilder category(SoundSource category) {
			this.category = category;
			return this;
		}

		public SoundEntryBuilder addVariant(String name) {
			return addVariant(VSCreateMod.asResource(name));
		}

		public SoundEntryBuilder addVariant(ResourceLocation id) {
			variants.add(id);
			return this;
		}

		public SoundEntryBuilder playExisting(SoundEvent event) {
			return playExisting(event, 1, 1);
		}

		public SoundEntryBuilder playExisting(SoundEvent event, float volume, float pitch) {
			return playExisting(() -> event, volume, pitch);
		}

		public SoundEntryBuilder playExisting(Supplier<SoundEvent> event, float volume, float pitch) {
			wrappedEvents.add(new ConfiguredSoundEvent(event, volume, pitch));
			return this;
		}

		public SoundEntry build() {
			SoundEntry entry =
					wrappedEvents.isEmpty() ? new CustomSoundEntry(id,
																   variants,
																   subtitle,
																   category,
																   attenuationDistance)
											: new WrappedSoundEntry(id,
																	subtitle,
																	wrappedEvents,
																	category,
																	attenuationDistance);
			ALL.put(entry.getId(), entry);
			return entry;
		}

	}

	public static abstract class SoundEntry {

		protected ResourceLocation id;
		protected String subtitle;
		protected SoundSource category;
		protected int attenuationDistance;

		public SoundEntry(ResourceLocation id, String subtitle, SoundSource category, int attenuationDistance) {
			this.id = id;
			this.subtitle = subtitle;
			this.category = category;
			this.attenuationDistance = attenuationDistance;
		}

		public abstract void register();

		public abstract void write(JsonObject json);

		public abstract SoundEvent getMainEvent();

		public String getSubtitleKey() {
			return id.getNamespace() + ".subtitle." + id.getPath();
		}

		public ResourceLocation getId() {
			return id;
		}

		public boolean hasSubtitle() {
			return subtitle != null;
		}

		public String getSubtitle() {
			return subtitle;
		}

		public void playOnServer(Level world, Vec3i pos) {
			playOnServer(world, pos, 1, 1);
		}

		public void playOnServer(Level world, Vec3i pos, float volume, float pitch) {
			play(world, null, pos, volume, pitch);
		}

		public void play(Level world, Player entity, Vec3i pos, float volume, float pitch) {
			play(world, entity, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, volume, pitch);
		}

		public abstract void play(Level world, Player entity, double x, double y, double z, float volume, float pitch);

		public void play(Level world, Player entity, Vec3i pos) {
			play(world, entity, pos, 1, 1);
		}

		public void playFrom(Entity entity) {
			playFrom(entity, 1, 1);
		}

		public void playFrom(Entity entity, float volume, float pitch) {
            if (!entity.isSilent()) {
                play(entity.level, null, entity.blockPosition(), volume, pitch);
            }
		}

		public void play(Level world, Player entity, Vec3 pos, float volume, float pitch) {
			play(world, entity, pos.x(), pos.y(), pos.z(), volume, pitch);
		}

		public void playAt(Level world, Vec3i pos, float volume, float pitch, boolean fade) {
			playAt(world, pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, volume, pitch, fade);
		}

		public abstract void playAt(Level world, double x, double y, double z, float volume, float pitch, boolean fade);

		public void playAt(Level world, Vec3 pos, float volume, float pitch, boolean fade) {
			playAt(world, pos.x(), pos.y(), pos.z(), volume, pitch, fade);
		}

	}

	private static class WrappedSoundEntry extends SoundEntry {

		private final List<ConfiguredSoundEvent> wrappedEvents;
		private final List<CompiledSoundEvent> compiledEvents;

		public WrappedSoundEntry(ResourceLocation id, String subtitle,
								 List<ConfiguredSoundEvent> wrappedEvents, SoundSource category, int attenuationDistance) {
			super(id, subtitle, category, attenuationDistance);
			this.wrappedEvents = wrappedEvents;
			compiledEvents = new ArrayList<>();
		}

		@Override
		public void register() {
			for (int i = 0; i < wrappedEvents.size(); i++) {
				ConfiguredSoundEvent wrapped = wrappedEvents.get(i);
				ResourceLocation location = getIdOf(i);
				SoundEvent event = new SoundEvent(location);
				compiledEvents.add(new CompiledSoundEvent(event, wrapped.volume(), wrapped.pitch()));
			}

			for (CompiledSoundEvent event : compiledEvents) {
				sounds.register(event.event.getLocation().getPath(), () -> event.event);
			}
		}

		protected ResourceLocation getIdOf(int i) {
			return new ResourceLocation(id.getNamespace(), i == 0 ? id.getPath() : id.getPath() + "_compounded_" + i);
		}

		@Override
		public void write(JsonObject json) {
			for (int i = 0; i < wrappedEvents.size(); i++) {
				ConfiguredSoundEvent event = wrappedEvents.get(i);
				JsonObject entry = new JsonObject();
				JsonArray list = new JsonArray();
				JsonObject s = new JsonObject();
				s.addProperty("name", event.event()
										   .get()
										   .getLocation()
										   .toString());
				s.addProperty("type", "event");
                if (attenuationDistance != 0) {
                    s.addProperty("attenuation_distance", attenuationDistance);
                }
				list.add(s);
				entry.add("sounds", list);
                if (i == 0 && hasSubtitle()) {
                    entry.addProperty("subtitle", getSubtitleKey());
                }
				json.add(getIdOf(i).getPath(), entry);
			}
		}

		@Override
		public SoundEvent getMainEvent() {
			return compiledEvents.get(0)
								 .event();
		}

		@Override
		public void play(Level world, Player entity, double x, double y, double z, float volume, float pitch) {
			for (CompiledSoundEvent event : compiledEvents) {
				world.playSound(entity, x, y, z, event.event(), category, event.volume() * volume,
								event.pitch() * pitch);
			}
		}

		@Override
		public void playAt(Level world, double x, double y, double z, float volume, float pitch, boolean fade) {
			for (CompiledSoundEvent event : compiledEvents) {
				world.playLocalSound(x, y, z, event.event(), category, event.volume() * volume,
									 event.pitch() * pitch, fade);
			}
		}

		private record CompiledSoundEvent(SoundEvent event, float volume, float pitch) {
		}

	}

	private static class CustomSoundEntry extends SoundEntry {

		protected List<ResourceLocation> variants;
		protected SoundEvent event;

		public CustomSoundEntry(ResourceLocation id, List<ResourceLocation> variants, String subtitle,
								SoundSource category, int attenuationDistance) {
			super(id, subtitle, category, attenuationDistance);
			this.variants = variants;
		}

		@Override
		public void register() {
			sounds.register(id.getPath(), () -> event = new SoundEvent(id));
		}

		@Override
		public void write(JsonObject json) {
			JsonObject entry = new JsonObject();
			JsonArray list = new JsonArray();

			JsonObject s = new JsonObject();
			s.addProperty("name", id.toString());
			s.addProperty("type", "file");
            if (attenuationDistance != 0) {
                s.addProperty("attenuation_distance", attenuationDistance);
            }
			list.add(s);

			for (ResourceLocation variant : variants) {
				s = new JsonObject();
				s.addProperty("name", variant.toString());
				s.addProperty("type", "file");
                if (attenuationDistance != 0) {
                    s.addProperty("attenuation_distance", attenuationDistance);
                }
				list.add(s);
			}

			entry.add("sounds", list);
            if (hasSubtitle()) {
                entry.addProperty("subtitle", getSubtitleKey());
            }
			json.add(id.getPath(), entry);
		}

		@Override
		public SoundEvent getMainEvent() {
			return event;
		}

		@Override
		public void play(Level world, Player entity, double x, double y, double z, float volume, float pitch) {
			world.playSound(entity, x, y, z, event, category, volume, pitch);
		}

		@Override
		public void playAt(Level world, double x, double y, double z, float volume, float pitch, boolean fade) {
			world.playLocalSound(x, y, z, event, category, volume, pitch, fade);
		}

	}

}
