package meew0.mewtwo.irc;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.UserLevel;
import org.pircbotx.output.OutputChannel;
import org.pircbotx.snapshot.ChannelSnapshot;

/**
 * Created by miras on 23.11.14.
 *
 * Class that pretends to be a channel but is actually a user.
 */
@SuppressWarnings("WeakerAccess")
public class UserChannel extends Channel {
    @SuppressWarnings("EmptyMethod")
    public class UserOutputChannel extends OutputChannel {
        private final User user;

        public UserOutputChannel(User user, Channel channel) {
            super(user.getBot(), channel);
            this.user = user;
        }

        public void part() {
        }
        public void part(String reason) {
        }

        public void message(User user, String message) {
            if (user == null)
                throw new IllegalArgumentException("Can't send message to null user");
            message(user.getNick() + ": " + message);
        }

        public void action(String action) {
            bot.sendIRC().action(user.getNick(), action);
        }

        public void notice(String notice) {
            bot.sendIRC().notice(user.getNick(), notice);
        }

        public void invite(Channel otherChannel) {
        }
        @SuppressWarnings("UnusedParameters")
        public void invite(User user) {
        }
        @SuppressWarnings("UnusedParameters")
        public void invite(String target) {
        }
        public void ctcpCommand(String command) {
        }
        public void cycle() {
        }
        public void cycle(final String key) {
        }
        public void who() {
        }
        public void getMode() {
        }
        public void setMode(String mode) {
        }
        public void setMode(String mode, Object... args) {
        }
        public void setMode(String mode, User user) {
        }
        public void setChannelLimit(int limit) {
        }
        public void removeChannelLimit() {
        }
        public void setChannelKey(String key) {
        }
        public void removeChannelKey(String key) {
        }
        public void setInviteOnly() {
        }
        public void removeInviteOnly() {
        }
        public void setModerated() {
        }
        public void removeModerated() {
        }
        public void setNoExternalMessages() {
        }
        public void removeNoExternalMessages() {
        }
        public void setSecret() {
        }
        public void removeSecret() {
        }
        public void setTopicProtection() {
        }
        public void removeTopicProtection() {
        }
        public void setChannelPrivate() {
        }
        public void removeChannelPrivate() {
        }
        public void ban(String hostmask) {
        }
        public void unBan(String hostmask) {
        }
        public void op(User user) {
        }
        public void deOp(User user) {
        }
        public void voice(User user) {
        }
        public void deVoice(User user) {
        }
        public void halfOp(User user) {
        }
        public void deHalfOp(User user) {
        }
        public void owner(User user) {
        }
        public void deOwner(User user) {
        }
        public void superOp(User user) {
        }
        public void deSuperOp(User user) {
        }
        public void setTopic(String topic) {
        }
        public void kick(User user) {
        }
        public void kick(User user, String reason) {
        }


        @Override
        public void message(String message) {
            user.send().message(message);
        }
    }
    private final User user;

    public UserChannel(User user) {
        super(user.getBot(), user.getBot().getUserChannelDao(), user.getNick());
        this.user = user;
    }

    public OutputChannel send() {
        return new UserOutputChannel(user, this);
    }
    protected void parseMode(String rawMode) {
    }
    public String getMode() {
        return "";
    }
    public boolean hasTopicProtection() {
        return false;
    }

    public ImmutableSortedSet<UserLevel> getUserLevels(User user) {
        return ImmutableSortedSet.of();
    }
    public ImmutableSortedSet<User> getNormalUsers() {
        return ImmutableSortedSet.of();
    }
    public ImmutableSortedSet<User> getOps() {
        return ImmutableSortedSet.of();
    }
    public ImmutableSortedSet<User> getVoices() {
        return ImmutableSortedSet.of();
    }
    public ImmutableSortedSet<User> getOwners() {
        return ImmutableSortedSet.of();
    }
    public ImmutableSortedSet<User> getHalfOps() {
        return ImmutableSortedSet.of();
    }
    public ImmutableSortedSet<User> getSuperOps() {
        return ImmutableSortedSet.of();
    }
    protected void setMode(String mode, ImmutableList<String> modeParsed) {
    }

    public ImmutableSortedSet<User> getUsers() {
        return ImmutableSortedSet.of(user);
    }

    public boolean isOp(User user) {
        return false;
    }
    public boolean hasVoice(User user) {
        return false;
    }
    public boolean isSuperOp(User user) {
        return false;
    }
    public boolean isOwner(User user) {
        return false;
    }
    public boolean isHalfOp(User user) {
        return false;
    }
    public ChannelSnapshot createSnapshot() {
        return new ChannelSnapshot(this, mode);
    }

    public int compareTo(Channel other) {
        return getName().compareToIgnoreCase(other.getName());
    }



}
