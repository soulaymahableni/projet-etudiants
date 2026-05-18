{{/*
Helpers reutilisables pour le Chart projet-etudiants.
*/}}

{{/* Nom commun du chart */}}
{{- define "projet-etudiants.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/* Nom complet avec release */}}
{{- define "projet-etudiants.fullname" -}}
{{- printf "%s-%s" .Release.Name (include "projet-etudiants.name" .) | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/* Labels communs applique a toutes les ressources */}}
{{- define "projet-etudiants.labels" -}}
app.kubernetes.io/name: {{ include "projet-etudiants.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
helm.sh/chart: {{ printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" }}
{{- end -}}
