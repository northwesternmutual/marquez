/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package marquez.service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import marquez.common.models.DatasetVersionId;
import marquez.common.models.JobName;
import marquez.common.models.JobVersionId;
import marquez.common.models.NamespaceName;
import marquez.common.models.RunId;
import marquez.common.models.RunState;

@EqualsAndHashCode
@ToString
@Slf4j
public final class Run {
  @Getter private final RunId id;
  @Getter private final Instant createdAt;
  @Getter private final Instant updatedAt;
  @Nullable private final Instant nominalStartTime;
  @Nullable private final Instant nominalEndTime;
  @Getter private final RunState state;
  @Nullable private final Instant startedAt;
  @Nullable private final Instant endedAt;
  @Nullable @Setter private Long durationMs;
  @Getter private final Map<String, String> args;
  private final String namespaceName;
  private final String jobName;
  private final UUID jobVersion;
  private final String location;
  @Getter private final List<DatasetVersionId> inputVersions;
  @Getter private final List<DatasetVersionId> outputVersions;
  @Getter private final Map<String, String> context;
  @Getter private final ImmutableMap<String, Object> facets;

  public Run(
      @NonNull final RunId id,
      @NonNull final Instant createdAt,
      @NonNull final Instant updatedAt,
      @Nullable final Instant nominalStartTime,
      @Nullable final Instant nominalEndTime,
      @NonNull final RunState state,
      @Nullable final Instant startedAt,
      @Nullable final Instant endedAt,
      @Nullable final Long durationMs,
      @Nullable final Map<String, String> args,
      String namespaceName, // Fields not serialized may be null for clients
      String jobName,
      UUID jobVersion,
      String location,
      List<DatasetVersionId> inputVersions,
      List<DatasetVersionId> outputVersions,
      Map<String, String> context,
      @Nullable final ImmutableMap<String, Object> facets) {
    this.id = id;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.nominalStartTime = nominalStartTime;
    this.nominalEndTime = nominalEndTime;
    this.state = state;
    this.startedAt = startedAt;
    this.endedAt = endedAt;
    this.durationMs = durationMs;
    this.args = (args == null) ? ImmutableMap.of() : args;
    this.namespaceName = namespaceName;
    this.jobName = jobName;
    this.jobVersion = jobVersion;
    this.location = location;
    this.inputVersions = inputVersions;
    this.outputVersions = outputVersions;
    this.context = context;
    this.facets = (facets == null) ? ImmutableMap.of() : facets;
  }

  public Optional<Instant> getNominalStartTime() {
    return Optional.ofNullable(nominalStartTime);
  }

  public Optional<Instant> getNominalEndTime() {
    return Optional.ofNullable(nominalEndTime);
  }

  public Optional<Instant> getStartedAt() {
    return Optional.ofNullable(startedAt);
  }

  public Optional<Instant> getEndedAt() {
    return Optional.ofNullable(endedAt);
  }

  public Optional<Long> getDurationMs() {
    return Optional.ofNullable(durationMs);
  }

  @JsonProperty
  public JobVersionId getJobVersion() {
    if (jobVersion == null || jobName == null || namespaceName == null) {
      return null;
    }
    return new JobVersionId(new NamespaceName(namespaceName), new JobName(jobName), jobVersion);
  }

  @JsonIgnore
  public String getNamespaceName() {
    return namespaceName;
  }

  @JsonIgnore
  public String getJobName() {
    return jobName;
  }

  @JsonIgnore
  public String getLocation() {
    return location;
  }
}
